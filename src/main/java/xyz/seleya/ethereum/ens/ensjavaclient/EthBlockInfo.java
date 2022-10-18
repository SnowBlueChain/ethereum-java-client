package xyz.seleya.ethereum.ens.ensjavaclient;

import java.math.BigInteger;
import java.util.Objects;

public class EthBlockInfo {
    private final BigInteger blockNumber;
    private final String blockHash;

    public EthBlockInfo(BigInteger blockNumber, String blockHash) {
        this.blockNumber = blockNumber;
        this.blockHash = blockHash;
    }

    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public String getBlockHash() {
        return blockHash;
    }


    @Override
    public String toString() {
        return "EthBlockInfo{" +
                "blockNumber=" + blockNumber +
                ", blockHash='" + blockHash + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EthBlockInfo that = (EthBlockInfo) o;
        return Objects.equals(blockNumber, that.blockNumber) && Objects.equals(blockHash, that.blockHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockNumber, blockHash);
    }
}
