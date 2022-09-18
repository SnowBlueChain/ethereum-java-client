package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Defines an interface for access ENS-related info from an Ethereum node.
 */
public interface EnsResolver {

    /**
     * Returns the content hash that's associated with the ENS domain name.
     *
     * @param ensName an ENS domain name.
     * @return a content hash, if any.
     */
    Optional<String> findContentHash(String ensName);

    /**
     * Check if the ENS domain name is valid.
     *
     * @param ensName an ENS domain name.
     * @return true if valid; otherwise, false.
     */
    boolean isValidEnsName(String ensName);

    /**
     * Returns the url in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a url, if any.
     */
    String getUrlInTextRecords(String contractId);

    /**
     * Returns the name of twitter in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a name of Twitter account, if any.
     */
    String getTwitterInTextRecords(String contractId);


    /**
     * Returns the name of github in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a name of github account, if any.
     */
    String getGithubInTextRecords(String contractId);

    /**
     * Returns the latest block number.
     *
     * @return block number from the most recent block on Ethereum mainnet.
     */
    Optional<BigInteger> getLatestBlockNumber();

}
