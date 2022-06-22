package xyz.seleya.ethereum.ens.ensjavaclient;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import xyz.seleya.ethereum.ens.contracts.generated.ENSRegistryWithFallback;

/**
 * Defines an interface to represent an ENS registry.
 */
public interface EnsRegistry {
    static EnsRegistryImplementation load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return EnsRegistryImplementation.load(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    RemoteFunctionCall<String> resolver(byte[] node);
}
