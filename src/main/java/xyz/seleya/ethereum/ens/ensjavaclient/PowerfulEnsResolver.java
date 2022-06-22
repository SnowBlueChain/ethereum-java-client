package xyz.seleya.ethereum.ens.ensjavaclient;

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

public class PowerfulEnsResolver implements EnsResolver {

    private static final Logger log = getLogger(PowerfulEnsResolver.class);

    private final Web3j web3j;
    private final int addressLength;
    private final TransactionManager transactionManager;
    private long syncThreshold; // non-final in case this value needs to be tweaked

    public PowerfulEnsResolver(Web3j web3j, long syncThreshold, int addressLength) {
        this.web3j = web3j;
        this.addressLength = addressLength;
        this.transactionManager = new ClientTransactionManager(web3j, null); // don't use empty string
        this.syncThreshold = syncThreshold;
    }

    public PowerfulEnsResolver(Web3j web3j, long syncThreshold) {
        this(web3j, syncThreshold, Keys.ADDRESS_LENGTH_IN_HEX);
    }

    public PowerfulEnsResolver(Web3j web3j) {
        this(web3j, DEFAULT_SYNC_THRESHOLD);
    }

    public void setSyncThreshold(long syncThreshold) {
        this.syncThreshold = syncThreshold;
    }

    public long getSyncThreshold() {
        return syncThreshold;
    }

    /**
     * Provides an access to a valid public resolver in order to access other API methods.
     *
     * @param ensName our user input ENS name
     * @return PublicResolver
     */
    protected PublicResolver obtainPublicResolver(String ensName) {
        if (isValidEnsName(ensName, addressLength)) {
            try {
                if (!isSynced()) {
                    throw new EnsResolutionException("Node is not currently synced");
                } else {
                    return lookupResolver(ensName);
                }
            } catch (Exception e) {
                throw new EnsResolutionException("Unable to determine sync status of node", e);
            }

        } else {
            throw new EnsResolutionException("EnsName is invalid: " + ensName);
        }
    }

    public String resolve(String contractId) {
        if (isValidEnsName(contractId, addressLength)) {
            PublicResolver resolver = obtainPublicResolver(contractId);

            byte[] nameHash = NameHash.nameHashAsBytes(contractId);
            String contractAddress = null;
            try {
                contractAddress = resolver.addr(nameHash).send();

                // Get the raw contenthash from ENS
                final byte[] contentHash = resolver.contenthash(nameHash).send();

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
                Cid cid = Cid.build(0L, Cid.Codec.DagProtobuf, multihash);
                 // CID version 1
//                Cid cid = Cid.build(1L, Cid.Codec.DagProtobuf, multihash);
                log.info("contentHash ipfs cid: " + cid);
//                final String ipfsContentId = Cid.decode(contentHashText).toString();
//                log.info("ipfsContentId text: " + ipfsContentId);
                
                final String textRecordsUrl = resolver.text(nameHash, "url").send();
                log.info("textRecordsUrl: " + textRecordsUrl);
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
            PublicResolver resolver = obtainPublicResolver(reverseName);

            byte[] nameHash = NameHash.nameHashAsBytes(reverseName);
            String name;
            try {
                name = resolver.name(nameHash).send();
            } catch (Exception e) {
                throw new RuntimeException("Unable to execute Ethereum request", e);
            }

            if (!isValidEnsName(name, addressLength)) {
                throw new RuntimeException("Unable to resolve name for address: " + address);
            } else {
                return name;
            }
        } else {
            throw new EnsResolutionException("Address is invalid: " + address);
        }
    }

    public String contentHash(String contractId) {
        Cid cid = null;
        if (isValidEnsName(contractId, addressLength)) {
            PublicResolver resolver = obtainPublicResolver(contractId);
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
        return cid.toString();
    }

    boolean isSynced() throws Exception {
        EthSyncing ethSyncing = web3j.ethSyncing().send();
        if (ethSyncing.isSyncing()) {
            return false;
        } else {
            EthBlock ethBlock =
                    web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            long timestamp = ethBlock.getBlock().getTimestamp().longValueExact() * 1000;

            return System.currentTimeMillis() - syncThreshold < timestamp;
        }
    }

    public static boolean isValidEnsName(String input) {
        return isValidEnsName(input, Keys.ADDRESS_LENGTH_IN_HEX);
    }

    public static boolean isValidEnsName(String input, int addressLength) {
        return input != null // will be set to null on new Contract creation
                && (input.contains(".") || !WalletUtils.isValidAddress(input, addressLength));
    }

    private PublicResolver lookupResolver(String ensName) throws Exception {
        NetVersion netVersion = web3j.netVersion().send();
        String registryContract = Contracts.resolveRegistryContract(netVersion.getNetVersion());

        ENSRegistryWithFallback ensRegistry =
                ENSRegistryWithFallback.load(registryContract, web3j, transactionManager, new DefaultGasProvider());

        byte[] nameHash = NameHash.nameHashAsBytes(ensName);
        String resolverAddress = ensRegistry.resolver(nameHash).send();

        return PublicResolver.load(
                resolverAddress, web3j, transactionManager, new DefaultGasProvider());
    }

    @Override
    public Optional<String> findContentHash(@NonNull final String contractId) {
        return Optional.empty();
    }
}
