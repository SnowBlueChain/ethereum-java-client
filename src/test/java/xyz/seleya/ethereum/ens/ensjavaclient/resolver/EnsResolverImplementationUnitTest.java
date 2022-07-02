package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import com.google.gson.Gson;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EnsResolverImplementationUnitTest {
    private final static String ENS_NAME_KOHORST_ETH = "kohorst.eth";
    private final static String NON_ENS_NAME_HELLO_COM = "hellocom";
    private final static String NULL_CASE = null;

    private EnsResolverImplementation ensResolverImplementationTestInstance;
    private Web3j web3jTestInstance;
    private static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUpALl() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @BeforeEach
    void setUp() {
        final String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        this.web3jTestInstance = Web3j.build(new HttpService(baseUrl));
        this.ensResolverImplementationTestInstance = EnsResolverImplementation.getInstance(this.web3jTestInstance);
    }

    @AfterAll
    static void tearDown() throws IOException {
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
    void isSynced_true_happycase() throws Exception {
        // Set up mocked response for eth_sync request
        String stubbedResponseEthSyncFalse = new Utilities().getEthSyncFalseResponse();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(stubbedResponseEthSyncFalse)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for eth_getBlockByNumber request
        String stubbedResponseBlockNumber = new Utilities().getEthGetBlockByNumberResponse();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(stubbedResponseBlockNumber)
                .addHeader("Content-Type", "application/json"));

        // trigger the method call that is being tested
        assertTrue(ensResolverImplementationTestInstance.isSynced());

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));

        // requestBody: {"jsonrpc":"2.0","method":"eth_syncing","params":[],"id":0}
        String requestBody = lastRecordedRequest.getUtf8Body();
        Map<String, Object> jsonMap = new Gson().fromJson(requestBody, Map.class);
        Assert.assertTrue(jsonMap.containsKey("jsonrpc"));
        Assert.assertTrue("2.0".equals(jsonMap.get("jsonrpc")));
        Assert.assertTrue(jsonMap.containsKey("method"));
        Assert.assertTrue("eth_syncing".equals(jsonMap.get("method")));
        Assert.assertTrue(jsonMap.containsKey("id"));
        Assert.assertTrue(0 == ((Double) jsonMap.get("id")).intValue());
    }

    @Test
    void isSynced_false() throws Exception {
        String stubbedResponseEthSyncFalse = new Utilities().getEthSyncTrueResponse();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(stubbedResponseEthSyncFalse)
                .addHeader("Content-Type", "application/json"));

        // trigger the method call that is being tested
        assertFalse(ensResolverImplementationTestInstance.isSynced());

        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
//        Assert.assertTrue("/v1/exchange/info?id=270".equals(recordedRequest.getPath()));
    }

    @Test
    void lookupResolver_happycase() throws Exception {
        String stubbedResponse = new Utilities().getNetVersionResponse();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(stubbedResponse)
                .addHeader("Content-Type", "application/json"));

        // trigger the method call that is being tested
        ensResolverImplementationTestInstance.lookupResolver(ENS_NAME_KOHORST_ETH);
//        assertEquals(270, actual.getExchangeId());
//
//        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
//        Assert.assertTrue("GET".equals(recordedRequest.getMethod()));
//        Assert.assertTrue("/v1/exchange/info?id=270".equals(recordedRequest.getPath()));
    }
}
