package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This is test utility class. It returns faked json-rpc responses for Ethereum APIs.
 * <p />
 * @see <a href="https://ethereum.org/en/developers/docs/apis/json-rpc/">Ethereum Developers - json-rpc</a>
 * @see <a href="https://docs.infura.io/infura/networks/ethereum/json-rpc-methods">Infura Ethereum doc - json-rpc</a>
 */
public class FakeEthereumJsonRpcResponseCreator {

    private static final String JSON_FILE_BASE_PATH = "ethereumjsonrpcresponses";
    private static final String NET_VERSION_JSON_FILE = "/net_version.json";
    private static final String ETH_SYNC_TRUE_JSON_FILE = "/eth_sync_true.json";
    private static final String ETH_SYNC_FALSE_JSON_FILE = "/eth_sync_false.json";
    private static final String ETH_GET_BLOCK_BY_NUMBER_JSON_FILE = "/eth_getBlockByNumber.json";
    private static final String ETH_CALL_ENS_TEXT_JSON_FILE = "/eth_call_ens_text_kohorst_eth.json";
    private static final String ETH_CALL_ENS_RESOLVER_JSON_FILE = "/eth_call_ens_resolver_ens.json";

    public String getNetVersion() throws IOException, URISyntaxException {
        return getInfoResponse(JSON_FILE_BASE_PATH + NET_VERSION_JSON_FILE);
    }

    public String getEthSyncTrue() throws IOException, URISyntaxException {
        return getInfoResponse(JSON_FILE_BASE_PATH + ETH_SYNC_TRUE_JSON_FILE);
    }

    public String getEthSyncFalse() throws IOException, URISyntaxException {
        return getInfoResponse(JSON_FILE_BASE_PATH + ETH_SYNC_FALSE_JSON_FILE);
    }

    public String getEthGetBlockByNumber() throws IOException, URISyntaxException {
        return getInfoResponse(JSON_FILE_BASE_PATH + ETH_GET_BLOCK_BY_NUMBER_JSON_FILE);
    }

    public String getEthCallEnsTextKohorstEth() throws IOException, URISyntaxException {
        return getInfoResponse(JSON_FILE_BASE_PATH + ETH_CALL_ENS_TEXT_JSON_FILE);
    }

    public String getEthCallResolverEns() throws IOException, URISyntaxException {
        return getInfoResponse(JSON_FILE_BASE_PATH + ETH_CALL_ENS_RESOLVER_JSON_FILE);
    }

    // Convert JSON file to String
    private String getInfoResponse (String filename) throws IOException, URISyntaxException {
        // get the file handle of the json file
        File theJsonFile = getFileFromResource(filename);
        // read a string out of the file handle
        BufferedReader reader = new BufferedReader(new FileReader(theJsonFile));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        // delete the last new line separator
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();

        String content = stringBuilder.toString();
        return content;
    }

    /*
       The resource URL is not working in the JAR
       If we try to access a file that is inside a JAR,
       It throws NoSuchFileException (linux), InvalidPathException (Windows)

       Resource URL Sample: file:java-io.jar!/json/file1.json
    */
    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }
    }
}
