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

import java.math.BigInteger;
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
    public void getLocationInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getLocationInTextRecords("digitalpratik.eth");
        assertEquals("Ahmedabad, Gujarat, India", actual.get());
    }

    @Test
    public void getNoticeInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getNoticeInTextRecords("digitalpratik.eth");
        assertEquals("this is not for sale, okay!", actual.get());
    }

    @Test
    public void getNameInTextRecords_happycase() {
        final Optional<String> actual = ensResolverImplementationTestInstance.getNameInTextRecords("digitalpratik.eth");
        assertEquals("Digital Pratik", actual.get());
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
}