package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import com.google.common.annotations.VisibleForTesting;
import io.ipfs.cid.Cid;
import io.ipfs.multihash.Multihash;
import org.slf4j.Logger;
import org.springframework.lang.NonNull;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;
import org.web3j.ens.Contracts;
import org.web3j.ens.EnsResolutionException;
import org.web3j.ens.NameHash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthSyncing;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;
import xyz.seleya.ethereum.ens.contracts.generated.ENSRegistryWithFallback;
import xyz.seleya.ethereum.ens.contracts.generated.PublicResolver;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static org.web3j.ens.EnsResolver.DEFAULT_SYNC_THRESHOLD;
import static org.web3j.ens.EnsResolver.REVERSE_NAME_SUFFIX;

public class EnsResolverImplementation implements EnsResolver {

    private static final Logger log = getLogger(EnsResolverImplementation.class);

    private final Web3j web3j;
    private final int addressLength;
    private final TransactionManager transactionManager;
    private long syncThreshold; // non-final in case this value needs to be tweaked

    private EnsResolverImplementation(Web3j web3j, long syncThreshold, int addressLength) {
        this.web3j = web3j;
        this.syncThreshold = syncThreshold;
        this.addressLength = addressLength;
        this.transactionManager = new ClientTransactionManager(web3j, null); // don't use empty string
    }

    private EnsResolverImplementation(Web3j web3j, long syncThreshold) {
        this(web3j, syncThreshold, Keys.ADDRESS_LENGTH_IN_HEX);
    }

    private EnsResolverImplementation(Web3j web3j) {
        this(web3j, DEFAULT_SYNC_THRESHOLD);
    }

    public static EnsResolverImplementation getInstance(Web3j web3j) {
        EnsResolverImplementation instance = new EnsResolverImplementation(web3j);
        return instance;
    }

    @Override
    public boolean isValidEnsName(String input) {
        return input != null
                && (input.contains(".") || WalletUtils.isValidAddress(input, this.addressLength));
    }

    @Override
    public Optional<String> findContentHash(@NonNull final String contractId) {
        Cid cid = null;
        if (isValidEnsName(contractId)) {
            PublicResolver resolver = lookupResolver(contractId);
            byte[] nameHash = NameHash.nameHashAsBytes(contractId);
            try {
                byte[] contentHash = resolver.contenthash(nameHash).send();
                // Convert the raw contenthash into binary format
                final String contentHashHexString = Numeric.toHexString(contentHash);
                log.info("contentHash in binary format: " + contentHashHexString);

                // binary format: 0xe30101701220ba6c1d3e724a3449101474f8dbaf94565985da0ec07240911cdc5bde9f0d55f5
                // 0xe301: ipfs
                // 0x01: version
                // 0x70: DagProtobuf
                // 0x12: sha2_256
                // 0x20: the length of the hash
                final String actualHash = contentHashHexString.substring(14);
                log.info("actualHash: " + actualHash);

                // Build the multihash with sha2_256 and the actual hash
                Multihash multihash = new Multihash(Multihash.Type.sha2_256, Numeric.hexStringToByteArray(actualHash));
                log.info("multihash: " + multihash);

                // CID version 0
                cid = Cid.build(0L, Cid.Codec.DagProtobuf, multihash);
                // CID version 1
//                Cid cid = Cid.build(1L, Cid.Codec.DagProtobuf, multihash);
                log.info("contentHash ipfs cid: " + cid);
//                final String ipfsContentId = Cid.decode(contentHashText).toString();
//                log.info("ipfsContentId text: " + ipfsContentId);

            } catch (Exception e) {
                throw new RuntimeException("Unable to execute Ethereum request", e);
            }
        }
        return Optional.ofNullable(cid.toString());
    }

    public String resolve(String contractId) {
        if (isValidEnsName(contractId)) {
            PublicResolver resolver = lookupResolver(contractId);

            byte[] nameHash = NameHash.nameHashAsBytes(contractId);
            String contractAddress = null;
            try {
                contractAddress = resolver.addr(nameHash).send();
            } catch (Exception e) {
                throw new RuntimeException("Unable to execute Ethereum request", e);
            }

            if (!WalletUtils.isValidAddress(contractAddress)) {
                throw new RuntimeException("Unable to resolve address for name: " + contractId);
            } else {
                return contractAddress;
            }
        } else {
            return contractId;
        }
    }

    /**
     * Reverse name resolution as documented in the <a
     * href="https://docs.ens.domains/contract-api-reference/reverseregistrar">specification</a>.
     *
     * @param address an ethereum address, example: "0x00000000000C2E074eC69A0dFb2997BA6C7d2e1e"
     * @return a EnsName registered for provided address
     */
    public String reverseResolve(String address) {
        if (WalletUtils.isValidAddress(address, addressLength)) {
            String reverseName = Numeric.cleanHexPrefix(address) + REVERSE_NAME_SUFFIX;
            PublicResolver resolver = lookupResolver(reverseName);

            byte[] nameHash = NameHash.nameHashAsBytes(reverseName);
            String name;
            try {
                name = resolver.name(nameHash).send();
            } catch (Exception e) {
                throw new RuntimeException("Unable to execute Ethereum request", e);
            }

            if (!isValidEnsName(name)) {
                throw new RuntimeException("Unable to resolve name for address: " + address);
            } else {
                return name;
            }
        } else {
            throw new EnsResolutionException("Address is invalid: " + address);
        }
    }

    @VisibleForTesting
    boolean isSynced() throws Exception {
        EthSyncing ethSyncing = web3j.ethSyncing().send();
        if (ethSyncing.isSyncing()) {
            // return false since syncing in progress
            return false;
        } else {
            EthBlock ethBlock =
                    web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            long timestamp = ethBlock.getBlock().getTimestamp().longValueExact() * 1000;

            return System.currentTimeMillis() - syncThreshold < timestamp;
        }
    }

    @VisibleForTesting
    PublicResolver lookupResolver(String ensName) throws EnsResolutionException {
        if (isValidEnsName(ensName)) {
            try {
                if (!isSynced()) {
                    throw new EnsResolutionException("Node is not currently synced");
                } else {
                    NetVersion netVersion = web3j.netVersion().send();
                    String registryContract = Contracts.resolveRegistryContract(netVersion.getNetVersion());

                    ENSRegistryWithFallback ensRegistry =
                            ENSRegistryWithFallback.load(registryContract, web3j, transactionManager, new DefaultGasProvider());

                    byte[] nameHash = NameHash.nameHashAsBytes(ensName);
                    String resolverAddress = ensRegistry.resolver(nameHash).send();

                    return PublicResolver.load(
                            resolverAddress, web3j, transactionManager, new DefaultGasProvider());
                }
            } catch (Exception e) {
                throw new EnsResolutionException("Unable to determine sync status of node", e);
            }

        } else {
            throw new EnsResolutionException("EnsName is invalid: " + ensName);
        }
    }

    // TODO refactor find Url, Twitter, Github in text records
    public String findTextRecords(@NonNull final String ethDomainName, String key) {
        PublicResolver resolver = lookupResolver(ethDomainName);
        byte[] nameHash = NameHash.nameHashAsBytes(ethDomainName);
        try {
            String textRecords = resolver.text(nameHash, key).send();
            log.info("text record - " + key + " : " + key);
            return textRecords;
        } catch (Exception e) {
            return "";
        }
    }

    // text records
//    public String findUrlInTextRecords(@NonNull final String ethDomainName) {
//        PublicResolver resolver = lookupResolver(ethDomainName);
//        byte[] nameHash = NameHash.nameHashAsBytes(ethDomainName);
//        try {
//            String url = resolver.text(nameHash, "url").send();
//            log.info("text record - url: " + url);
//            return url;
//        } catch (Exception e) {
//            //throw new RuntimeException("Unable to execute Ethereum request", e);
//            return null;
//        }
//    }
//
//    public String findTwitterInTextRecords(@NonNull final String contractId) {
//        if (isValidEnsName(contractId)) {
//            PublicResolver resolver = lookupResolver(contractId);
//            byte[] nameHash = NameHash.nameHashAsBytes(contractId);
//            try {
//                String twitter = resolver.text(nameHash, "vnd.twitter").send();
//                log.info("text record - twitter: " + twitter);
//                return twitter;
//            } catch (Exception e) {
//                throw new RuntimeException("Unable to execute Ethereum request", e);
//            }
//        }
//        return "";
//    }
//
//    public String findGithubInTextRecords(@NonNull final String contractId) {
//        if (isValidEnsName(contractId)) {
//            PublicResolver resolver = lookupResolver(contractId);
//            byte[] nameHash = NameHash.nameHashAsBytes(contractId);
//            try {
//                String github = resolver.text(nameHash, "vnd.github").send();
//                log.info("text record - github: " + github);
//                return github;
//            } catch (Exception e) {
//                throw new RuntimeException("Unable to execute Ethereum request", e);
//            }
//        }
//        return "";
//    }
}
