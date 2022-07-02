package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.web3j.protocol.Web3j;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EnsResolverImplementationUnitTest {
    private final static String ENS_NAME_KOHORST_ETH = "kohorst.eth";
    private final static String NON_ENS_NAME_HELLO_COM = "hellocom";
    private final static String NULL_CASE = null;

    private EnsResolverImplementation ensResolverImplementationTestInstance;

    @Mock
    private Web3j mockWeb3j;

    @BeforeEach
    void setUp() {
        this.ensResolverImplementationTestInstance = EnsResolverImplementation.getInstance(mockWeb3j);
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
}
