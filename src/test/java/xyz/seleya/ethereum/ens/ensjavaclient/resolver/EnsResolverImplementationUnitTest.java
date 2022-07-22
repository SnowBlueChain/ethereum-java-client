package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import com.google.gson.Gson;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EnsResolverImplementationUnitTest {
    private final static String ENS_NAME_KOHORST_ETH = "kohorst.eth";
    private final static String NON_ENS_NAME_HELLO_COM = "hellocom";
    private final static String NULL_CASE = null;

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
    void isValidEnsNae_unhappycase() {
        boolean actual = ensResolverImplementationTestInstance.isValidEnsName(NON_ENS_NAME_HELLO_COM);
        assertFalse(actual);
    }

    @Test
    void findUrlInTextRecords_happycase() throws Exception {
        // Set up mocked response for eth_sync request
        String stubbedResponseEthSyncFalse = new FakeEthereumJsonRpcResponseCreator().getEthSyncFalse();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthSyncFalse)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for eth_getBlockByNumber request
        String stubbedResponseBlockNumber = new FakeEthereumJsonRpcResponseCreator().getEthGetBlockByNumber();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseBlockNumber)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for net_version
        String stubbedResponse = new FakeEthereumJsonRpcResponseCreator().getNetVersion();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponse)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for ens_resolver
        String stubbedResponseEthCallResolverEns = new FakeEthereumJsonRpcResponseCreator().getEthCallResolverEns();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallResolverEns)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for ens_text
        String stubbedResponseEthCallEnsTextUrlKohorstEth =
                new FakeEthereumJsonRpcResponseCreator().getEthCallEnsTextUrlKohorstEth();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallEnsTextUrlKohorstEth)
                .addHeader("Content-Type", "application/json"));

        // trigger the method call that is being tested
        final String actual = ensResolverImplementationTestInstance.findUrlInTextRecords(ENS_NAME_KOHORST_ETH);

        assertEquals("https://lucaskohorst.com", actual);

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    // find twitter in text records
    @Test
    void findTwitterInTextRecords_happycase() throws Exception {
        // Set up mocked response for eth_sync request
        String stubbedResponseEthSyncFalse = new FakeEthereumJsonRpcResponseCreator().getEthSyncFalse();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthSyncFalse)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for eth_getBlockByNumber request
        String stubbedResponseBlockNumber = new FakeEthereumJsonRpcResponseCreator().getEthGetBlockByNumber();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseBlockNumber)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for net_version
        String stubbedResponse = new FakeEthereumJsonRpcResponseCreator().getNetVersion();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponse)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for ens_resolver
        String stubbedResponseEthCallResolverEns = new FakeEthereumJsonRpcResponseCreator().getEthCallResolverEns();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallResolverEns)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for ens_text_twitter
        String stubbedResponseEthCallEnsTextTwitterKohorstEth =
                new FakeEthereumJsonRpcResponseCreator().getEthCallEnsTextTwitterKohorstEth();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallEnsTextTwitterKohorstEth)
                .addHeader("Content-Type", "application/json"));

        // trigger the method call that is being tested
        final String actual = ensResolverImplementationTestInstance.findTwitterInTextRecords(ENS_NAME_KOHORST_ETH);

        assertEquals("KohorstLucas", actual);

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    // find github in text records
    @Test
    void findGithubInTextRecords_happycase() throws Exception {
        // Set up mocked response for eth_sync request
        String stubbedResponseEthSyncFalse = new FakeEthereumJsonRpcResponseCreator().getEthSyncFalse();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthSyncFalse)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for eth_getBlockByNumber request
        String stubbedResponseBlockNumber = new FakeEthereumJsonRpcResponseCreator().getEthGetBlockByNumber();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseBlockNumber)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for net_version
        String stubbedResponse = new FakeEthereumJsonRpcResponseCreator().getNetVersion();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponse)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for ens_resolver
        String stubbedResponseEthCallResolverEns = new FakeEthereumJsonRpcResponseCreator().getEthCallResolverEns();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallResolverEns)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for ens_text_github
        String stubbedResponseEthCallEnsTextGithubKohorstEth =
                new FakeEthereumJsonRpcResponseCreator().getEthCallEnsTextGithubKohorstEth();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallEnsTextGithubKohorstEth)
                .addHeader("Content-Type", "application/json"));

        // trigger the method call that is being tested
        final String actual = ensResolverImplementationTestInstance.findGithubInTextRecords(ENS_NAME_KOHORST_ETH);

        assertEquals("Kohorst-Lucas", actual);

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }


    @Test
    void isSynced_true_happycase() throws Exception {
        // Set up mocked response for eth_sync request
        String stubbedResponseEthSyncFalse = new FakeEthereumJsonRpcResponseCreator().getEthSyncFalse();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthSyncFalse).addHeader("Content-Type", "application/json"));

        // Set up mocked response for eth_getBlockByNumber request
        String stubbedResponseBlockNumber = new FakeEthereumJsonRpcResponseCreator().getEthGetBlockByNumber();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseBlockNumber).addHeader("Content-Type", "application/json"));

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
        String stubbedResponseEthSyncTrue = new FakeEthereumJsonRpcResponseCreator().getEthSyncTrue();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthSyncTrue).addHeader("Content-Type", "application/json"));

        // trigger the method call that is being tested
        assertFalse(ensResolverImplementationTestInstance.isSynced());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    /**
     * TODO not working yet
     *
     * @throws Exception
     */
    @Test
    void lookupResolver_happycase() throws Exception {
        String stubbedResponse = new FakeEthereumJsonRpcResponseCreator().getNetVersion();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponse).addHeader("Content-Type", "application/json"));
    }
}
