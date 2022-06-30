package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

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
}
