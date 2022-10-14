package xyz.seleya.ethereum.ens.ensjavaclient;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class EthLogInfo {
    String address;
    String blockHash;
    BigInteger blockNumber;
    String data;
    BigInteger logIndex;
    boolean removed;
    List<String> topics;
    String transactionHash;
    BigInteger transactionIndex;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(BigInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BigInteger getLogIndex() {
        return logIndex;
    }

    public void setLogIndex(BigInteger logIndex) {
        this.logIndex = logIndex;
    }

    public boolean isRemoved(boolean removed) {
        return this.removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public BigInteger getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(BigInteger transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    @Override
    public String toString() {
        return "EthLogInfo{" +
                "address='" + address + '\'' +
                ", blockHash='" + blockHash + '\'' +
                ", blockNumber=" + blockNumber +
                ", data='" + data + '\'' +
                ", logIndex=" + logIndex +
                ", removed=" + removed +
                ", topics=" + topics +
                ", transactionHash='" + transactionHash + '\'' +
                ", transactionIndex=" + transactionIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EthLogInfo that = (EthLogInfo) o;
        return removed == that.removed && Objects.equals(address, that.address) && Objects.equals(blockHash, that.blockHash) && Objects.equals(blockNumber, that.blockNumber) && Objects.equals(data, that.data) && Objects.equals(logIndex, that.logIndex) && Objects.equals(topics, that.topics) && Objects.equals(transactionHash, that.transactionHash) && Objects.equals(transactionIndex, that.transactionIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, blockHash, blockNumber, data, logIndex, removed, topics, transactionHash, transactionIndex);
    }
}
