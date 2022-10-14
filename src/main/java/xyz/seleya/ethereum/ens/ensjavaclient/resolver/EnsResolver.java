package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import xyz.seleya.ethereum.ens.ensjavaclient.EthLogInfo;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
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
    Optional<String> getUrlInTextRecords(String contractId);

    /**
     * Returns the name of twitter in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a name of Twitter account, if any.
     */
    Optional<String> getTwitterInTextRecords(String contractId);


    /**
     * Returns the name of github in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a name of github account, if any.
     */
    Optional<String> getGithubInTextRecords(String contractId);

    /**
     * Returns the name of avatar in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a name of avatar account, if any.
     */
    Optional<String> getAvatarInTextRecords(String contractId);

    /**
     * Returns the description in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a description, if any.
     */
    Optional<String> getDescriptionInTextRecords(String contractId);

    /**
     * Returns the name of display in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a name of display, if any.
     */
    Optional<String> getDisplayInTextRecords(String contractId);

    /**
     * Returns the email in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return an email, if any.
     */
    Optional<String> getEmailInTextRecords(String contractId);

    /**
     * Returns the keywords in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return the keywords, if any.
     */
    Optional<String> getKeywordsInTextRecords(String contractId);

    /**
     * Returns the mail address in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a mail address, if any.
     */
    Optional<String> getMailInTextRecords(String contractId);


    /**
     * Returns the name in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a name, if any.
     */
    Optional<String> getNameInTextRecords(String contractId);


    /**
     * Returns the notice in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a notice, if any.
     */
    Optional<String> getNoticeInTextRecords(String contractId);

    /**
     * Returns the location in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a location, if any.
     */
    Optional<String> getLocationInTextRecords(String contractId);

    /**
     * Returns the phone# in text records that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a phone#, if any.
     */
    Optional<String> getPhoneInTextRecords(String contractId);

    /**
     * Returns all the information in textrecords.
     *
     * @param contractId an ENS domain name (a.k.a. ensName).
     * @return a map if any. The key is the keyword in textrecords and value is the corresponding result.
     */
    Map<String, String> getMetadata(String contractId);


    /**
     * Returns the latest block number.
     *
     * @return block number from the most recent block on Ethereum mainnet.
     */
    Optional<BigInteger> getLatestBlockNumber();

    /**
     * Returns the current gas price.
     *
     * @return the gas price from the most recent block on Ethereum mainnet.
     */
    Optional<BigInteger> getGasPrice();

    /**
     * Returns the number of peers currently connected to the client.
     *
     * @return the number of peers currently connected to the client on Ethereum mainnet.
     */
    Optional<String> getCurrentClientVersion();

    /**
     * Returns the number of peer count.
     *
     * @return the number of peer count from the most recent block on Ethereum mainnet.
     */
    Optional<BigInteger> getNetPeerCount();


    /**
     * Returns the balance of the account of given address.
     * @param ensName an ENS domain name.
     *
     * @return Returns the balance of the account of given address.
     */
    Optional<BigInteger> getBalance(String ensName);

    /**
     * Returns an array of all logs matching a given filter object.
     * @param ensName an ENS domain name.
     *
     * @return Returns a list of all logs matching a given filter object.
     */
    List<EthLogInfo> getLogs(String ensName);

}
