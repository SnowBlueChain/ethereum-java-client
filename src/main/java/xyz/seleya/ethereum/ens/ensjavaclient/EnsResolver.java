package xyz.seleya.ethereum.ens.ensjavaclient;

import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * Defines an interface for access ENS-related info from an Ethereum node.
 */
public interface EnsResolver {

    /**
     * Returns the content hash that's associated with the ENS domain name.
     *
     * @param contractId an ENS domain name.
     * @return a content hash, if any.
     */
    Optional<String> findContentHash(@NonNull final String contractId);
}
