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
import org.web3j.ens.EnsResolutionException;
import org.web3j.ens.NameHash;
import org.web3j.protocol.Web3j;

import org.web3j.protocol.http.HttpService;
import xyz.seleya.ethereum.ens.contracts.generated.PublicResolver;
import xyz.seleya.ethereum.ens.ensjavaclient.textrecords.GlobalKey;
import xyz.seleya.ethereum.ens.ensjavaclient.textrecords.ServiceKey;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EnsResolverImplementationUnitTest {
    private final static String ENS_NAME_KOHORST_ETH = "kohorst.eth";
    private final static String NON_ENS_NAME_HELLO_COM = "hellocom";
    private final static String NULL_CASE = null;
    private final static String NON_EXISTING_ENS_NAME = "nonexistingnameofjklmn.eth";
    private final static boolean HAPPYCASE = true;
    private final static boolean UNHAPPYCASE = false;

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

    // Set up for Eth Block Number (find the latest block number)
    public void setupMockedResponseEthLatestBlockNumber() throws  Exception {
        String stubbedResponseEthBlockNumber = new FakeEthereumJsonRpcResponseCreator().getEthLatestBlockNumberJsonFile();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthBlockNumber)
                .addHeader("Content-Type", "application/json"));
    }


    @Test
    void getUrlInTextRecords_happycase() throws Exception {
        // Set up mocked response for
        // 1) eth_sync request 2) eth_getBlockByNumber request 3) net_version
        // 4) resolver 5) text with keyword
        setupMockedEthSync(HAPPYCASE);
        setupResponseBlockNumber();
        setupResponseNetVersion ();
        setupMockedResponseEthCallResolver(HAPPYCASE);
        setupMockedResponseEthCallTextKohorstEth(GlobalKey.URL.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final String actual = ensResolverImplementationTestInstance.findTextRecords(ENS_NAME_KOHORST_ETH, GlobalKey.URL.getKey());

        assertEquals("https://lucaskohorst.com", actual);

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
        setupMockedResponseEthCallTextKohorstEth(GlobalKey.URL.getKey(), HAPPYCASE);

        Executable executable = () -> ensResolverImplementationTestInstance.findTextRecords(NON_ENS_NAME_HELLO_COM, GlobalKey.URL.getKey());
        Assertions.assertThrows(EnsResolutionException.class, executable);
    }

    /**
     * @throws Exception Exception was handled as null.
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
        setupMockedResponseEthCallTextKohorstEth(GlobalKey.URL.getKey(), UNHAPPYCASE);

        String actual = ensResolverImplementationTestInstance.findTextRecords(NON_EXISTING_ENS_NAME, GlobalKey.URL.getKey());
        Assertions.assertEquals(null, actual);
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
        setupMockedResponseEthCallTextKohorstEth(ServiceKey.TWITTER.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final String actual = ensResolverImplementationTestInstance.findTextRecords(ENS_NAME_KOHORST_ETH, ServiceKey.TWITTER.getKey());

        assertEquals("KohorstLucas", actual);

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
        setupMockedResponseEthCallTextKohorstEth(ServiceKey.GITHUB.getKey(), HAPPYCASE);

        // trigger the method call that is being tested
        final String actual = ensResolverImplementationTestInstance.findTextRecords(ENS_NAME_KOHORST_ETH, ServiceKey.GITHUB.getKey());

        assertEquals("Kohorst-Lucas", actual);

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
        setupMockedResponseEthCallTextKohorstEth(ServiceKey.TWITTER.getKey(), HAPPYCASE);

        final String actual = ensResolverImplementationTestInstance.findTextRecords(ENS_NAME_KOHORST_ETH, ServiceKey.TWITTER.getKey());

        assertEquals("KohorstLucas", actual);

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void findLatestBlockNumber_happycase() throws Exception {
        setupMockedResponseEthLatestBlockNumber();

        final BigInteger actual = ensResolverImplementationTestInstance.getLatestBlockNumber();
        final BigInteger expected = new BigInteger("15561295");
        final int result = expected.compareTo(actual);
        Assert.assertTrue(result <= 0);
    }
}
