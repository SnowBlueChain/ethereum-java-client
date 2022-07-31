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
import org.web3j.protocol.Web3j;

import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import xyz.seleya.ethereum.ens.contracts.generated.PublicResolver;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EnsResolverImplementationUnitTest {
    private final static String ENS_NAME_KOHORST_ETH = "kohorst.eth";
    private final static String NON_ENS_NAME_HELLO_COM = "hellocom";
    private final static String NULL_CASE = null;

    private final static String NON_EXISTING_ENS_NAME = "nonexistingfjkejri.eth";

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

    private void setupMockedResponse(String key) throws Exception {
        // Set up mocked response for eth_sync request
        String stubbedResponseEthSyncFalse = new FakeEthereumJsonRpcResponseCreator().getEthSyncFalse();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthSyncFalse)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for eth_getBlockByNumber request
        String stubbedResponseBlockNumber = new FakeEthereumJsonRpcResponseCreator().getEthGetBlockByNumber();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseBlockNumber)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for net_version
        String stubbedResponseNetVersion = new FakeEthereumJsonRpcResponseCreator().getNetVersion();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseNetVersion)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for ens_resolver
        String stubbedResponseEthCallResolverEns = new FakeEthereumJsonRpcResponseCreator().getEthCallResolverEns();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallResolverEns)
                .addHeader("Content-Type", "application/json"));


        // Set up mocked response for key = "url", "vnd.twitter", "vnd.github"
        String stubbedResponseEthCallEnsTextKohorstEth =
                new FakeEthereumJsonRpcResponseCreator().getEthCallEnsTextKohorstEth(key);
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallEnsTextKohorstEth)
                .addHeader("Content-Type", "application/json"));
    }

    @Test
    void getUrlInTextRecords_happycase() throws Exception {
        setupMockedResponse("url");

        // trigger the method call that is being tested
        final String actual = ensResolverImplementationTestInstance.findTextRecords(ENS_NAME_KOHORST_ETH, "url");

        assertEquals("https://lucaskohorst.com", actual);

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }

    @Test
    void getUrlInTextRecords_ThrowEnsResolutionException_When_EnsName_Invalid() throws Exception {
        setupMockedResponse("url");

        Executable executable = () -> ensResolverImplementationTestInstance.findTextRecords(NON_ENS_NAME_HELLO_COM, "url");
        Assertions.assertThrows(EnsResolutionException.class, executable);
    }

    /**
     * @throws Exception
     */
    @Test
    void getUrlInTextRecords_ThrowEnsResolutionException_With_NonExisting_EnsName() throws Exception {
//        setupMockedResponse("url");

        // Set up mocked response for eth_sync request
        String stubbedResponseEthSyncFalse = new FakeEthereumJsonRpcResponseCreator().getEthSyncFalse();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthSyncFalse)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for eth_getBlockByNumber request
        String stubbedResponseBlockNumber = new FakeEthereumJsonRpcResponseCreator().getEthGetBlockByNumber();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseBlockNumber)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for net_version
        String stubbedResponseNetVersion = new FakeEthereumJsonRpcResponseCreator().getNetVersion();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseNetVersion)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for ens_resolver
        String stubbedResponseEthCallResolverNonExistingEns = new FakeEthereumJsonRpcResponseCreator().getEthCallResolverNonExistingEns();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallResolverNonExistingEns)
                .addHeader("Content-Type", "application/json"));


        // Set up mocked response for key = "url", "vnd.twitter", "vnd.github"
        String stubbedResponseEthCallEnsTextNonExistingKohorstEth =
                new FakeEthereumJsonRpcResponseCreator().getEthCallEnsTextNonExistingKohorstEth("url");
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallEnsTextNonExistingKohorstEth)
                .addHeader("Content-Type", "application/json"));

        String actual = ensResolverImplementationTestInstance.findTextRecords(NON_EXISTING_ENS_NAME, "url");
        Assertions.assertEquals(null, actual);
    }


    // find twitter in text records
    @Test
    void getTwitterInTextRecords_happycase() throws Exception {
        setupMockedResponse("vnd.twitter");

        // trigger the method call that is being tested
        final String actual = ensResolverImplementationTestInstance.findTextRecords(ENS_NAME_KOHORST_ETH, "vnd.twitter");

        assertEquals("KohorstLucas", actual);

        // Verify the last request to mockBackEnd
        RecordedRequest lastRecordedRequest = mockBackEnd.takeRequest();
        Assert.assertTrue("POST".equals(lastRecordedRequest.getMethod()));
        Assert.assertTrue("/".equals(lastRecordedRequest.getPath()));
    }


    // find github in text records
    @Test
    void getGithubInTextRecords_happycase() throws Exception {
        setupMockedResponse("vnd.github");

        // trigger the method call that is being tested
        final String actual = ensResolverImplementationTestInstance.findTextRecords(ENS_NAME_KOHORST_ETH, "vnd.github");

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


    @Test
    void lookupResolver_happycase() throws Exception {
        // Set up mocked response for eth_sync request
        String stubbedResponseEthSyncFalse = new FakeEthereumJsonRpcResponseCreator().getEthSyncFalse();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthSyncFalse)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for eth_getBlockByNumber request
        String stubbedResponseBlockNumber = new FakeEthereumJsonRpcResponseCreator().getEthGetBlockByNumber();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseBlockNumber)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for net_version
        String stubbedResponseNetVersion = new FakeEthereumJsonRpcResponseCreator().getNetVersion();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseNetVersion)
                .addHeader("Content-Type", "application/json"));

        // Set up mocked response for ens_resolver
        String stubbedResponseEthCallResolverEns = new FakeEthereumJsonRpcResponseCreator().getEthCallResolverEns();
        mockBackEnd.enqueue(new MockResponse().setBody(stubbedResponseEthCallResolverEns)
                .addHeader("Content-Type", "application/json"));

        PublicResolver actual = ensResolverImplementationTestInstance.lookupResolver(ENS_NAME_KOHORST_ETH);

        String expectedContractAddress = "0x4976fb03c32e5b8cfe2b6ccb31c09ba78ebaba41";
        String actualContractAddress = actual.getContractAddress();

        // trigger the method call that is being tested
        assertEquals(expectedContractAddress, actualContractAddress);

    }




}
