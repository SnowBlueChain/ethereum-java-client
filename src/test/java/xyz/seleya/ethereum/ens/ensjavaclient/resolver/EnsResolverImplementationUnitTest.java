package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.web3j.protocol.Web3j;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EnsResolverImplementationUnitTest {
    private final static String ENS_NAME_KOHORST_ETH = "kohorst.eth";

    private EnsResolverImplementation ensResolverImplementationTestInstance;

    @Mock
    private Web3j mockWeb3j;

    @BeforeEach
    void setUp() {
        this.ensResolverImplementationTestInstance = EnsResolverImplementation.getInstance(mockWeb3j);
    }

    @Test
    void isValidEnsName_happycase() {
        // trigger the call
        boolean actual = ensResolverImplementationTestInstance.isValidEnsName(ENS_NAME_KOHORST_ETH);
        assertTrue(actual);
    }
}
