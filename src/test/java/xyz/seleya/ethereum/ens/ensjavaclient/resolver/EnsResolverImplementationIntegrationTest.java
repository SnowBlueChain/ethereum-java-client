package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.web3j.ens.EnsResolutionException;
import org.web3j.ens.NameHash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnsResolverImplementationIntegrationTest {

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
        final String actual = ensResolverImplementationTestInstance.getUrlInTextRecords("kohorst.eth");
        assertEquals("https://lucaskohorst.com", actual);
    }

    @Test
    public void getUrlInTextRecords_unhappycase() {
        final String actual = ensResolverImplementationTestInstance.getUrlInTextRecords("324tgadgae454wq.eth");
        assertEquals(null, actual);
    }

    @Test
    public void getTwitterInTextRecords_happycase() {
        final String actual = ensResolverImplementationTestInstance.getTwitterInTextRecords("kohorst.eth");
        assertEquals("KohorstLucas", actual);
    }

    @Test
    public void getGithubInTextRecords_happycase() {
        final String actual = ensResolverImplementationTestInstance.getGithubInTextRecords("kohorst.eth");
        assertEquals("Kohorst-Lucas", actual);
    }
}