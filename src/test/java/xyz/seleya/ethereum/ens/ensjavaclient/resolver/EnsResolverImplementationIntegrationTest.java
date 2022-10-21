package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.web3j.ens.EnsResolutionException;
import org.web3j.ens.NameHash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import xyz.seleya.ethereum.ens.ensjavaclient.EthBlockInfo;
import xyz.seleya.ethereum.ens.ensjavaclient.EthLogInfo;

import javax.swing.plaf.metal.OceanTheme;
import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(EnsResolverImplementationIntegrationTest.TestConfig.class)
public class EnsResolverImplementationIntegrationTest {

    // Comment to be added
    @SpringBootConfiguration
    @ComponentScan("xyz.seleya")
    public static class TestConfig {
    }

    @LocalServerPort
    private int testServerPort;
    private final static String TEST_URL = System.getenv("INFURA_API_KEY_URL");

    private EnsResolverImplementation ensResolverImplementationTestInstance;

    private Web3j web3j;

    @BeforeEach
    void setUp() {
        web3j = Web3j.build(new HttpService(TEST_URL));
        ensResolverImplementationTestInstance = EnsResolverImplementation.getInstance(web3j);
    }

    @Test
    public void getUrlInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getUrlInTextRecords("kohorst.eth");
        assertEquals("https://lucaskohorst.com", actual.get());
    }

    @Test
    public void getUrlInTextRecords_unhappycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getUrlInTextRecords("324tgadgae454wq.eth");
        assertEquals(Optional.empty(), actual);
    }

    @Test
    public void getTwitterInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getTwitterInTextRecords("kohorst.eth");
        assertEquals("KohorstLucas", actual.get());
    }

    @Test
    public void getGithubInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getGithubInTextRecords("kohorst.eth");
        assertEquals("Kohorst-Lucas", actual.get());
    }

    @Test
    public void getAvatarInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getAvatarInTextRecords("digitalpratik.eth");
        assertEquals("eip155:1/erc1155:0x009fe5cbD30f17699E7ee5D6Df73117677aeDE51/1", actual.get());
    }

    @Test
    public void getDescriptionInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getDescriptionInTextRecords("digitalpratik.eth");
        assertTrue(actual.get().startsWith("Digital Pratik Reminder for you:"));
    }

    @Test
    public void getDisplayInTextRecord_emptyresultcase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getDisplayInTextRecords("digitalpratik.eth");
        assertEquals("", actual.get());
    }

    @Test
    public void getEmailInTextRecord_emptyresultcase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getEmailInTextRecords("digitalpratik.eth");
        assertEquals("", actual.get());
    }

    @Test
    public void getKeywordsInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getKeywordsInTextRecords("digitalpratik.eth");
        //assertEquals("ens, digital pratik, pratik", actual.get());
        assertTrue(actual.get().contains("ens"));
        assertTrue(actual.get().contains("digital pratik"));
        assertTrue(actual.get().contains("pratik"));

    }

    @Test
    public void getMailInTextRecords_emptyresultcase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getMailInTextRecords("digitalpratik.eth");
        assertEquals("", actual.get());
    }

    @Test
    public void getNameInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getNameInTextRecords("digitalpratik.eth");
        assertEquals("Digital Pratik", actual.get());
    }

    @Test
    public void getNoticeInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getNoticeInTextRecords("digitalpratik.eth");
        assertEquals("this is not for sale, okay!", actual.get());
    }

    @Test
    public void getLocationInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getLocationInTextRecords("digitalpratik.eth");
        assertEquals("Ahmedabad, Gujarat, India", actual.get());
    }

    @Test
    public void getPhoneInTextRecords_emptyresultcase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getPhoneInTextRecords("digitalpratik.eth");
        assertEquals("", actual.get());
    }

    @Test
    public void findContentHash_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.findContentHash("kohorst.eth");
        assertTrue(actual.isPresent());
        assertEquals("QmatNA86VTCzVW5UAo37gdb6KY344ZwN3ngPfe7qEBdtBe", actual.get());
    }

    @Test
    public void findContentHash_bad_ens_name() {
        final Optional<String> actual = ensResolverImplementationTestInstance.findContentHash("324tgadgae454wq.eth");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void findContentHash_empty_string() {
        final Optional<String> actual = ensResolverImplementationTestInstance.findContentHash("");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void findLatestBlockNumber_happycase() throws Exception {
        final Optional<BigInteger> actual = ensResolverImplementationTestInstance.getLatestBlockNumber();
        assertTrue(actual.isPresent());
        BigInteger actualResult = actual.get();
        assertTrue(actualResult.compareTo(new BigInteger("15561295")) >= 0);
    }

    @Test
    public void getMetadata_happycase() throws Exception {
        final Map<String, String> actualMap = ensResolverImplementationTestInstance.getMetadata("digitalpratik.eth");
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("avatar", "eip155:1/erc1155:0x009fe5cbD30f17699E7ee5D6Df73117677aeDE51/1");
        expectedMap.put("description", "Digital Pratik Reminder for you: No matter what, Always keep smiling :)");
        expectedMap.put("display", "Not Found" );
        expectedMap.put("email", "Not Found");
        expectedMap.put("keywords", "ens, digital pratik, pratik" );
        expectedMap.put("mail","Not Found" );
        expectedMap.put("name", "Digital Pratik");
        expectedMap.put("notice", "this is not for sale, okay!" );
        expectedMap.put("location","Ahmedabad, Gujarat, India");
        expectedMap.put("phone", "Not Found");
        expectedMap.put("url","https://opensea.io/collection/jorrparivar" );
        expectedMap.put("vnd.github","Not Found" );
        expectedMap.put("vnd.twitter", "Not Found" );

        for(String keyword : actualMap.keySet()) {
            if (keyword.equals("description")) {
                assertTrue(actualMap.get(keyword).startsWith("Digital Pratik Reminder for you"));
            } else {
                assertEquals(actualMap.get(keyword), expectedMap.get(keyword));
            }
        }
    }
    @Test
    public void getGasPrice_happycase() throws Exception {
        final Optional<BigInteger> actual = ensResolverImplementationTestInstance.getGasPrice();
        BigInteger expected = new BigInteger("13020289782");
        assertTrue(actual.isPresent());
        BigInteger actualResult = actual.get();
        assertTrue(actualResult.compareTo(new BigInteger("0")) > 0);
    }

    @Test
    public void getCurrentClientVersion_happycase() throws Exception {
        final Optional<String> actual = ensResolverImplementationTestInstance.getCurrentClientVersion();
        String expected = "Geth/v1.10.23-omnibus-b38477ec/linux-amd64/go1.18.5";
        assertTrue(actual.isPresent());
        String actualResult = actual.get();
        assertEquals(expected, actualResult);
    }

    @Test
    public void getNetPeerCount_happycase() throws Exception {
        final Optional<BigInteger> actual = ensResolverImplementationTestInstance.getNetPeerCount();
        BigInteger expected = new BigInteger("100");
        assertTrue(actual.isPresent());
        BigInteger actualResult = actual.get();
        assertTrue(actualResult.compareTo(new BigInteger("0")) >= 0);
    }

    @Test
    public void getBalance_happycase() throws Exception {
        String ensName = "kohorst.eth";
        final Optional<BigInteger> actualEthBalance = ensResolverImplementationTestInstance.getBalance(ensName);
        assertTrue(actualEthBalance.isPresent());
        final BigInteger actualBalance = actualEthBalance.get();
        assertTrue(actualBalance.compareTo(new BigInteger("0")) >= 0);
    }

    @Test
    public void getLogs_happycase() throws Exception {
        String ensName = "kohorst.eth";
        final List<EthLogInfo> ethLogInfoList = ensResolverImplementationTestInstance.getLogs(ensName);
        assertTrue(ethLogInfoList.size() > 0);
        EthLogInfo logObject = ethLogInfoList.get(0);
        assertEquals("0xda7a203806a6be3c3c4357c38e7b3aaac47f5dd2", logObject.getAddress());
    }

    @Test
    public void getTransactionCount_happycase() throws Exception {
        String ensName = "kohorst.eth";
        final Optional<BigInteger> ethGetTransactionCount = ensResolverImplementationTestInstance.getTransactionCount(ensName);
        assertTrue(ethGetTransactionCount.isPresent());
        BigInteger actual = ethGetTransactionCount.get();
        BigInteger expected = new BigInteger("1");
        assertEquals(expected, actual);
    }

    @Test
    public void getBlockTransactionCountByHash_happycase() throws Exception {
        String blockHash = "0x30791966b5a0bdd3376279400512b32bb8ef54e0769ce3dd6c74b2744dcbd808";
        final Optional<BigInteger> ethGetBlockTransactionCountByHash = ensResolverImplementationTestInstance.getBlockTransactionCountByHash(blockHash);
        assertTrue(ethGetBlockTransactionCountByHash.isPresent());
        BigInteger actual = ethGetBlockTransactionCountByHash.get();
        BigInteger expected = new BigInteger("b2", 16);
        assertEquals(expected, actual);
    }

    @Test
    public void getEthBlockInfo_happycase() throws Exception {
        String ensName = "kohorst.eth";
        final List<EthBlockInfo> ethBlockInfoList = ensResolverImplementationTestInstance.getEthBlockInfoList(ensName);
        final BigInteger actualBlockNumber = ethBlockInfoList.get(0).getBlockNumber();
        final String actualBlockHash = ethBlockInfoList.get(0).getBlockHash();
        final BigInteger expectedBlockNumber = new BigInteger("8674788");
        final String expectedBlockHash = "0x519cd3dc1ef7bac389bd3637bfbe5a11e7c9eb0aa4d0221d609ebe7fe9a21a9c";
        assertEquals(expectedBlockNumber, actualBlockNumber);
        assertEquals(expectedBlockHash, actualBlockHash);
    }

    @Test
    public void getEthBlockByHash_happycase() throws Exception {
        String blockHash = "0x30791966b5a0bdd3376279400512b32bb8ef54e0769ce3dd6c74b2744dcbd808";
        boolean showDetail = false;
        final EthBlock ethBlock = ensResolverImplementationTestInstance.getBlockByHash(blockHash, showDetail);
        final EthBlock.Block block = ethBlock.getBlock();
        final String actualExtraDate = block.getExtraData();
        final String expectedExtraDate = "0x505059452d65746865726d696e652d7573322d32";
        assertEquals(expectedExtraDate, actualExtraDate);
    }

    @Test
    public void getEthTransactionByHash_happycase() throws Exception {
        String transactionHash = "0x48057cd90b29809c3aef4ba85db157b3195b28fd6d53dd29fbd4e6ea9b5737ed";
        final EthTransaction actualEthTransaction = ensResolverImplementationTestInstance.getTransactionByHash(transactionHash);
        final BigInteger actualBlockNumber = actualEthTransaction.getResult().getBlockNumber();
        final BigInteger expectedBlockNumber = BigInteger.valueOf(0x86fd69);
        assertEquals(expectedBlockNumber, actualBlockNumber);
    }
}