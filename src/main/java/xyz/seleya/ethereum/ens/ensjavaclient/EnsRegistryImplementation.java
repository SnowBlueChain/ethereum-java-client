package xyz.seleya.ethereum.ens.ensjavaclient;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import xyz.seleya.ethereum.ens.contracts.generated.ENSRegistryWithFallback;

import java.math.BigInteger;

final public class EnsRegistryImplementation extends ENSRegistryWithFallback implements EnsRegistry {
    public EnsRegistryImplementation(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public EnsRegistryImplementation(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(contractAddress, web3j, credentials, contractGasProvider);
    }

    public EnsRegistryImplementation(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public EnsRegistryImplementation(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static EnsRegistryImplementation load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EnsRegistryImplementation(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
