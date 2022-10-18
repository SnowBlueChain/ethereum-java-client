package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.web3j.ens.EnsResolutionException;
import org.web3j.protocol.Web3j;

import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByHash;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.http.HttpService;
import xyz.seleya.ethereum.ens.contracts.generated.PublicResolver;
import xyz.seleya.ethereum.ens.ensjavaclient.EthLogInfo;
import xyz.seleya.ethereum.ens.ensjavaclient.TextRecordsKey;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EnsResolverImplementationUnitTest {
    private final static String ENS_NAME_KOHORST_ETH = "kohorst.eth";
    private final static String NON_ENS_NAME_HELLO_COM = "hellocom";
    private final static String NULL_CASE = null;
    private final static String NON_EXISTING_ENS_NAME = "nonexistingnameofjklmn.eth";
    private final static boolean HAPPYCASE = true;
    private final static boolean UNHAPPYCASE = false;

    private final static String ENS_NAME_DIGITAL_PRATIK_ETH = "digitalpratik.eth";

    // Mock backend service
    private static MockWebServer mockBackEnd;

    private EnsResolverImplementation ensResolverImplementationTestInstance;

    private Web3j web3jTestInstance;

    @BeforeEach
    void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        final String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        this.web3jTestInstance = Web3j.build(new HttpService(baseUrl));
        this.ensResolverImplementationTestInstance = EnsResolverImplementation.getInstance(this.web3jTestInstance);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void isValidEnsName_happycase() {
        boolean actual = ensResolverImplementationTestInstance.isValidEnsName(ENS_NAME_KOHORST_ETH);
        assertTrue(actual);
    }

    @Test
    void isValidEnsNae_nullcase() {
        boolean actual = ensResolverImplementationTestInstance.isValidEnsName(NULL_CASE);
        assertFalse(actual);
    }

    @Test
    void isValidEnsNae_non_existing_name() {
        boolean actual = ensResolverImplementationTestInstance.isValidEnsName(NON_EXISTING_ENS_NAME);
        assertTrue(actual);
    }

    @Test
    void isValidEnsNae_unhappycase() {
        boolean actual = ensResolverImplementationTestInstance.isValidEnsName(NON_ENS_NAME_HELLO_COM);
        assertFalse(actual);
    }


    // Step 1: Set up mocked response for eth_sync request
    private void setupMockedEthSync(boolean isSynced) throws Exception {
        String stubbedResponseEthSync;
        if (isSynced) {
            stubbedResponseEthSync = new FakeEthereumJsonRpcResponseCreator().getEthSyncFalse();
        } else {
            stubbedResponseEthSync = new FakeEthereumJsonRpcResponseCreator().getEthSyncTrue();
        }
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthSync)
                .addHeader("Content-Type", "application/json"));
    }

    // Step 2: Set up mocked response for eth_getBlockByNumber request
    private void setupResponseBlockNumber () throws Exception {
        String stubbedResponseBlockNumber = new FakeEthereumJsonRpcResponseCreator().getEthGetBlockByNumber();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseBlockNumber)
                .addHeader("Content-Type", "application/json"));
    }

    // Step 3: Set up mocked response for net_version
    private void setupResponseNetVersion() throws Exception {
        String stubbedResponseNetVersion = new FakeEthereumJsonRpcResponseCreator().getNetVersion();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseNetVersion)
                .addHeader("Content-Type", "application/json"));
    }


    // Step 4: Set up mocked response for ens_resolver
    private void setupMockedResponseEthCallResolver(boolean happycase) throws Exception {
        String stubbedResponseEthCallResolverEns;
        if (happycase) {
            stubbedResponseEthCallResolverEns = new FakeEthereumJsonRpcResponseCreator().getEthCallResolverEns();
        } else {
            // Set up mocked response for ens_resolver OF NON-EXISTING ENS NAME
            stubbedResponseEthCallResolverEns = new FakeEthereumJsonRpcResponseCreator().getEthCallResolverNonExistingEns();
        }
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallResolverEns)
                .addHeader("Content-Type", "application/json"));
    }

    // Step 5: Set up mocked response for EthCall in Text with key = "url", "vnd.twitter", "vnd.github"
    private void setupMockedResponseEthCallTextKohorstEth(String key, boolean happycase) throws Exception {
        String stubbedResponseEthCallEnsTextKohorstEth;
        if (happycase) {
            stubbedResponseEthCallEnsTextKohorstEth =
                    new FakeEthereumJsonRpcResponseCreator().getEthCallEnsTextKohorstEth(key);
        } else {
            stubbedResponseEthCallEnsTextKohorstEth =
                    new FakeEthereumJsonRpcResponseCreator().getEthCallEnsTextNonExistingKohorstEth(key);
        }
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallEnsTextKohorstEth)
                .addHeader("Content-Type", "application/json"));
    }

    // Step 5.1: Set up mocked response for EthCall(digitalpratik.eth) in Text with key = "avatar"
    private void setupMockedResponseEthCallTextDigitalPratikEth(String key, boolean happycase) throws Exception {
        String stubbedResponseEthCallEnsTextDigitalPratikEth;
        if (happycase) {
            stubbedResponseEthCallEnsTextDigitalPratikEth =
                    new FakeEthereumJsonRpcResponseCreator().getEthCallEnsTextDigitalPratikEth(key);
        } else {
            stubbedResponseEthCallEnsTextDigitalPratikEth =
                    new FakeEthereumJsonRpcResponseCreator().getEthCallEnsTextNonExistingDigitalPratikEth(key);
        }
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallEnsTextDigitalPratikEth)
                .addHeader("Content-Type", "application/json"));
    }

    // Set up for Eth Block Number (find the latest block number)
    public void setupMockedResponseEthLatestBlockNumber() throws  Exception {
        String stubbedResponseEthBlockNumber = new FakeEthereumJsonRpcResponseCreator().getEthLatestBlockNumberJsonFile();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthBlockNumber)
                .addHeader("Content-Type", "application/json"));
    }

    // Set up for Eth Gas Price
    public void setupMockedResponseEthGasPrice() throws Exception {
        String stubbedResponseEthGasPrice = new FakeEthereumJsonRpcResponseCreator().getEthGasPriceJsonFile();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthGasPrice)
                .addHeader("Content-Type", "application/json"));
    }

    // Set up for Eth Client Version
    public void setupMockedResponseEthClientVersion() throws Exception {
        String stubbedResponseEthClientVersion = new FakeEthereumJsonRpcResponseCreator().getEthClientVersionJsonFile();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthClientVersion)
                .addHeader("Content-Type", "application/json"));
    }

    // Set up for Eth Net Peer Count
    public void setupMockedResponseEthNetPeerCount() throws Exception {
        String stubbedResponseEthNetPeerCount = new FakeEthereumJsonRpcResponseCreator().getNetPeerCountJsonFile();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthNetPeerCount)
                .addHeader("Content-Type", "application/json"));
    }

    // Set up for Eth Balance
    // First, set up Address Resolver
    private void setupMockedResponseEthResolveAddress(boolean happycase) throws Exception {
        String stubbedResponseEthResolverAddress;
        if (happycase) {
            stubbedResponseEthResolverAddress = new FakeEthereumJsonRpcResponseCreator().getEthResolveAddress();
        } else {
            // Set up mocked response for ens_resolver OF NON-EXISTING ENS NAME
            stubbedResponseEthResolverAddress = new FakeEthereumJsonRpcResponseCreator().getEthCallResolverNonExistingEns();
        }
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthResolverAddress)
                .addHeader("Content-Type", "application/json"));
    }

    // Second, set up for Eth Balance
    public void setupMockedResponseEthBalance() throws Exception {
        String stubbedResponseEthBalance = new FakeEthereumJsonRpcResponseCreator().getEthBalanceJsonFile();;
        mockBackEnd.enqueue((new MockResponse().setBody(stubbedResponseEthBalance)
                    .addHeader("Content-Type", "application/json")));
    }

    // Setup for Eth Logs. We need set for address resolver, but we can reuse the previous set up.
    private void setupMockedResponseEthLogs() throws Exception {
        String stubbedResponseEthLogs = new FakeEthereumJsonRpcResponseCreator().getEthLogsJsonFile();;
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthLogs)
                .addHeader("Content-Type", "application/json"));
    }


    private void setupMockedResponseEthGetTransactionCount() throws Exception {
        String stubbedResponseEthGetTransactionCount = new FakeEthereumJsonRpcResponseCreator().getEthGetTransactionCountJsonFile();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthGetTransactionCount)
                .addHeader("Content-Type", "application/json"));
    }

    private void setupMockedResponseGetBlockTransactionCountByHash() throws Exception {
        String stubbedResponseGetBlockTransactionCountByHash = new FakeEthereumJsonRpcResponseCreator().getBlockTransactionCountByHashJsonFile();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseGetBlockTransactionCountByHash)
                .addHeader("Content-Type", "application/json"));
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void getUrlInTextRecords_happycase() throws Exception {
        // Set up mocked response for
        // 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextKohorstEth(TextRecordsKey.URL.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getUrlInTextRecords(ENS_NAME_KOHORST_ETH);

        assertEquals("https://lucaskohorst.com", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void getUrlInTextRecords_ThrowEnsResolutionException_When_EnsName_Invalid() throws Exception {
        // Set up mocked response for
        // 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextKohorstEth(TextRecordsKey.URL.getKey(), HAPPYCASE);

        Executable executable = () -> ensResolverImplementationTestInstance.getUrlInTextRecords(NON_ENS_NAME_HELLO_COM);
        Assertions.assertThrows(EnsResolutionException.class, executable);
    }

    /**
     * @throws Exception Exception was handled as Optional.empty() an empty object.
     */
    @Test
    void getUrlInTextRecords_ThrowEnsResolutionException_With_NonExisting_EnsName() throws Exception {
        // Set up mocked response for
        // 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(UNHAPPYCASE);
        setupMockedResponseEthCallTextKohorstEth(TextRecordsKey.URL.getKey(), UNHAPPYCASE);

        Optional<String> actual = ensResolverImplementationTestInstance.getUrlInTextRecords(NON_EXISTING_ENS_NAME);
        Assertions.assertEquals(Optional.empty(), actual);
    }

    // find twitter in text records
    @Test
    void getTwitterInTextRecords_happycase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextKohorstEth(TextRecordsKey.TWITTER.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getTwitterInTextRecords(ENS_NAME_KOHORST_ETH);

        assertEquals("KohorstLucas", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }


    // find github in text records
    @Test
    void getGithubInTextRecords_happycase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextKohorstEth(TextRecordsKey.GITHUB.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getGithubInTextRecords(ENS_NAME_KOHORST_ETH);

        assertEquals("Kohorst-Lucas", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void getAvatarInTextRecords_happycase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.AVATAR.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getAvatarInTextRecords(ENS_NAME_DIGITAL_PRATIK_ETH);

        assertEquals("eip155:1/erc1155:0x009fe5cbD30f17699E7ee5D6Df73117677aeDE51/1", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void getDescriptionInTextRecords_happycase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.DESCRIPTION.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getDescriptionInTextRecords(ENS_NAME_DIGITAL_PRATIK_ETH);

        assertTrue(actual.get().startsWith("Digital Pratik Reminder for you:"));

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void getDisplayInTextRecords_emptyresultcase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.DISPLAY.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getDisplayInTextRecords(ENS_NAME_DIGITAL_PRATIK_ETH);

        assertEquals("", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void getEmailInTextRecords_emptyresultcase() throws Exception{
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.EMAIL.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getEmailInTextRecords(ENS_NAME_DIGITAL_PRATIK_ETH);

        assertEquals("", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void getKeywordsInTextRecords_happycase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.KEYWORDS.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getKeywordsInTextRecords(ENS_NAME_DIGITAL_PRATIK_ETH);

        //assertEquals("ens, digital pratik, pratik", actual.get());
        assertTrue(actual.get().contains("ens"));
        assertTrue(actual.get().contains("digital pratik"));
        assertTrue(actual.get().contains("pratik"));

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }


    @Test
    void getMailInTextRecords_emptyresultcase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.MAIL.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getMailInTextRecords(ENS_NAME_DIGITAL_PRATIK_ETH);

        assertEquals("", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }


    @Test
    void getNameInTextRecords_happycase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.NAME.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getNameInTextRecords(ENS_NAME_DIGITAL_PRATIK_ETH);

        assertEquals("Digital Pratik", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }


    @Test
    void getNoticeInTextRecords_happycase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.NOTICE.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getNoticeInTextRecords(ENS_NAME_DIGITAL_PRATIK_ETH);

        assertEquals("this is not for sale, okay!", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }


    @Test
    void getLocationInTextRecords_happycase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.LOCATION.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getLocationInTextRecords(ENS_NAME_DIGITAL_PRATIK_ETH);

        assertEquals("Ahmedabad, Gujarat, India", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void getPhoneInTextRecords_emptyresultcase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.PHONE.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final Optional<String> actual = ensResolverImplementationTestInstance.getPhoneInTextRecords(ENS_NAME_DIGITAL_PRATIK_ETH);

        assertEquals("", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void isSynced_true_happycase() throws Exception {
        // Set up mocked response for eth_sync request
        setupMockedEthSync(HAPPYCASE);

        // Set up mocked response for eth_getBlockByNumber request
        setupResponseBlockNumber();

        // trigger the method call that is being tested
        assertTrue(ensResolverImplementationTestInstance.isSynced());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void isSynced_false_happycase() throws Exception {
        // Set up mocked response for eth_sync request
        setupMockedEthSync(UNHAPPYCASE);

        // trigger the method call that is being tested
        assertFalse(ensResolverImplementationTestInstance.isSynced());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }


    @Test
    void lookupResolver_happycase() throws Exception {
        // Set up mocked response for
        // 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();

        // 4) Set up mocked response for ens_resolver (happycase)
        setupMockedResponseEthCallResolver(HAPPYCASE);

        PublicResolver actual = ensResolverImplementationTestInstance.lookupResolver(ENS_NAME_KOHORST_ETH);

        String expectedContractAddress = "0x4976fb03c32e5b8cfe2b6ccb31c09ba78ebaba41";
        String actualContractAddress = actual.getContractAddress();

        // trigger the method call that is being tested
        assertEquals(expectedContractAddress, actualContractAddress);
    }

    @Test
    void findTextRecords_happycase() throws Exception {
        // Set up mocked response for 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextKohorstEth(TextRecordsKey.TWITTER.getKey(), HAPPYCASE);

        final Optional<String> actual = ensResolverImplementationTestInstance.findTextRecords(ENS_NAME_KOHORST_ETH, TextRecordsKey.TWITTER.getKey());

        assertEquals("KohorstLucas", actual.get());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void findLatestBlockNumber_happycase() throws Exception {
        setupMockedResponseEthLatestBlockNumber();

        final Optional<BigInteger> actual = ensResolverImplementationTestInstance.getLatestBlockNumber();
        final BigInteger expected = new BigInteger("15561295");
        Assert.assertTrue(actual.isPresent());
        BigInteger actualResult = actual.get();
        final int result = expected.compareTo(actualResult);
        Assert.assertTrue(result <= 0);
    }

    @Test
    public void getMetaData_happycase() throws Exception {
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion();
        setupMockedResponseEthCallResolver(HAPPYCASE);

        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.AVATAR.getKey(), HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.DESCRIPTION.getKey(), HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.DISPLAY.getKey(), HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.EMAIL.getKey(), HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.KEYWORDS.getKey(), HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.MAIL.getKey(), HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.NAME.getKey(), HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.NOTICE.getKey(), HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.LOCATION.getKey(), HAPPYCASE);
        setupMockedResponseEthCallTextDigitalPratikEth(TextRecordsKey.PHONE.getKey(), HAPPYCASE);

        setupMockedResponseEthCallTextKohorstEth(TextRecordsKey.URL.getKey(), HAPPYCASE);
        setupMockedResponseEthCallTextKohorstEth(TextRecordsKey.GITHUB.getKey(), HAPPYCASE);
        setupMockedResponseEthCallTextKohorstEth(TextRecordsKey.TWITTER.getKey(), HAPPYCASE);


        final Map<String, String> actualMap = ensResolverImplementationTestInstance.getMetadata("digitalpratik.eth");

        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("avatar", "eip155:1/erc1155:0x009fe5cbD30f17699E7ee5D6Df73117677aeDE51/1");
        expectedMap.put("description", "Digital Pratik Reminder for you" );
        expectedMap.put("display", "Not Found" );
        expectedMap.put("email", "Not Found");
        expectedMap.put("keywords", "ens, digital pratik, pratik" );
        expectedMap.put("mail","Not Found" );
        expectedMap.put("name", "Digital Pratik");
        expectedMap.put("notice", "this is not for sale, okay!" );
        expectedMap.put("location","Ahmedabad, Gujarat, India");
        expectedMap.put("phone", "Not Found");
        expectedMap.put("url","https://lucaskohorst.com");
        expectedMap.put("vnd.github","Kohorst-Lucas");
        expectedMap.put("vnd.twitter", "KohorstLucas" );

        for(String keyword : actualMap.keySet()) {
            System.out.println("Keyword is : " + keyword);
            if (keyword.equals("description")) {
                assertTrue(actualMap.get(keyword).startsWith("Digital Pratik Reminder for you"));
            } else {
                assertEquals(expectedMap.get(keyword), actualMap.get(keyword));
            }
        }
    }

    @Test
    void getGasPrice_happycase() throws Exception {
        setupMockedResponseEthGasPrice();

        final Optional<BigInteger> actual = ensResolverImplementationTestInstance.getGasPrice();
        final BigInteger expected = new BigInteger("0");
        Assert.assertTrue(actual.isPresent());
        BigInteger actualResult = actual.get();
        final int result = expected.compareTo(actualResult);
        Assert.assertTrue(result <= 0);
    }

    @Test
    void getClientVersion_happycase() throws Exception {
        setupMockedResponseEthClientVersion();

        final Optional<String> actual = ensResolverImplementationTestInstance.getCurrentClientVersion();
        final String expected = "Geth/v1.10.23-omnibus-b38477ec/linux-amd64/go1.18.5";
        Assert.assertTrue(actual.isPresent());
        String actualResult = actual.get();
        assertEquals(expected, actualResult);
    }

    @Test
    void getNetPeerCount_happycase() throws Exception {
        setupMockedResponseEthNetPeerCount();

        final Optional<BigInteger> actual = ensResolverImplementationTestInstance.getNetPeerCount();
        final BigInteger expected = new BigInteger("100");
        Assert.assertTrue(actual.isPresent());
        BigInteger actualResult = actual.get();
        Assert.assertTrue(actualResult.compareTo(new BigInteger("0")) >= 0);

    }

    @Test
    void getEthBalance_happycase() throws Exception {
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthResolveAddress(HAPPYCASE);
        setupMockedResponseEthBalance();

        String address = ensResolverImplementationTestInstance.resolve(ENS_NAME_KOHORST_ETH);
        final EthGetBalance actualEthBalance = web3jTestInstance.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
        final BigInteger actualBalance = actualEthBalance.getBalance();

        final BigInteger expected = new BigInteger("8636179763969940");
        assertEquals(expected, actualBalance);
    }

    @Test
    void getEthLogs_happycase() throws Exception {
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthResolveAddress(HAPPYCASE);
        setupMockedResponseEthLogs();

        String address = ensResolverImplementationTestInstance.resolve(ENS_NAME_KOHORST_ETH);
        EthFilter ethFilter = new EthFilter(DefaultBlockParameter.valueOf("earliest"), null, address);
        final EthLog actualEthLogResult = web3jTestInstance.ethGetLogs(ethFilter).send();
        List<EthLog.LogResult> actual = actualEthLogResult.getResult();

        EthLogInfo ethLogInfo = new EthLogInfo();
        assertTrue(actual.size() > 0);
        EthLog.LogObject logObject = (EthLog.LogObject) actual.get(0);
        String expectedAddress = "0xda7a203806a6be3c3c4357c38e7b3aaac47f5dd2";
        assertEquals(expectedAddress, logObject.getAddress());
        BigInteger expectedBlockNumber = new BigInteger("845de4", 16);
        assertEquals(expectedBlockNumber, logObject.getBlockNumber());
        BigInteger expectedLogIndex = new BigInteger("c6", 16);
        assertEquals(expectedLogIndex, logObject.getLogIndex());
    }

    @Test
    void getTransactionCount_happycase() throws Exception {
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthResolveAddress(HAPPYCASE);
        setupMockedResponseEthGetTransactionCount();

        String address = ensResolverImplementationTestInstance.resolve(ENS_NAME_KOHORST_ETH);
        final EthGetTransactionCount ethGetTransactionCount = web3jTestInstance.ethGetTransactionCount(address, DefaultBlockParameter.valueOf("latest")).send();
        final BigInteger actual = ethGetTransactionCount.getTransactionCount();
        final BigInteger expected = new BigInteger("1");
        assertEquals(expected, actual);
    }

    @Test
    void getBlockTransactionCountByHash() throws Exception {
        setupMockedResponseGetBlockTransactionCountByHash();
        String blockHash = "0x30791966b5a0bdd3376279400512b32bb8ef54e0769ce3dd6c74b2744dcbd808";
        final EthGetBlockTransactionCountByHash ethGetBlockTransactionCountByHash = web3jTestInstance.ethGetBlockTransactionCountByHash(blockHash).send();
        final BigInteger actual = ethGetBlockTransactionCountByHash.getTransactionCount();
        final BigInteger expected = new BigInteger("b2", 16);
        assertEquals(expected, actual);
    }
}