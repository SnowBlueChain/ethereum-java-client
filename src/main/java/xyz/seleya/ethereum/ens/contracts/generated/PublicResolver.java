package xyz.seleya.ethereum.ens.contracts.generated;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class PublicResolver extends Contract {
    public static final String BINARY = "6101006040523480156200001257600080fd5b5060405162001b9138038062001b91833981016040819052620000359162000071565b6001600160a01b0393841660805291831660a052821660c0521660e052620000d9565b6001600160a01b03811681146200006e57600080fd5b50565b600080600080608085870312156200008857600080fd5b8451620000958162000058565b6020860151909450620000a88162000058565b6040860151909350620000bb8162000058565b6060860151909250620000ce8162000058565b939692955090935050565b60805160a05160c05160e051611a776200011a6000396000610f6201526000610f30015260008181611021015261106e01526000610faa0152611a776000f3fe608060405234801561001057600080fd5b506004361061012c5760003560e01c806377372213116100ad578063c869023311610071578063c8690233146102a5578063d5fa2b00146102e2578063e59d895d146102f5578063e985e9c514610308578063f1cb7e061461034457600080fd5b806377372213146102395780638b95dd711461024c578063a22cb4651461025f578063ac9650d814610272578063bc1c58d11461029257600080fd5b8063304e6ade116100f4578063304e6ade146101cd5780633b3b57de146101e057806359d1d43c146101f3578063623195b014610213578063691f34311461022657600080fd5b806301ffc9a71461013157806310f13a8c14610159578063124a319c1461016e5780632203ab561461019957806329cd62ea146101ba575b600080fd5b61014461013f3660046113ab565b610357565b60405190151581526020015b60405180910390f35b61016c610167366004611408565b610368565b005b61018161017c366004611482565b610415565b6040516001600160a01b039091168152602001610150565b6101ac6101a73660046114ae565b61064d565b60405161015092919061152c565b61016c6101c836600461154d565b610768565b61016c6101db366004611579565b6107e8565b6101816101ee3660046115c5565b610847565b610206610201366004611579565b610879565b60405161015091906115de565b61016c6102213660046115f1565b61093e565b6102066102343660046115c5565b6109bf565b61016c610247366004611579565b610a61565b61016c61025a36600461165a565b610ac0565b61016c61026d366004611736565b610b8a565b610285610280366004611774565b610c64565b60405161015091906117e9565b6102066102a03660046115c5565b610d7f565b6102cd6102b33660046115c5565b600090815260056020526040902080546001909101549091565b60408051928352602083019190915201610150565b61016c6102f036600461184b565b610d9c565b61016c610303366004611870565b610dc3565b6101446103163660046118b0565b6001600160a01b03918216600090815260076020908152604080832093909416825291909152205460ff1690565b6102066103523660046114ae565b610e50565b600061036282610efe565b92915050565b8461037281610f23565b61037b57600080fd5b82826006600089815260200190815260200160002087876040516103a09291906118de565b9081526040519081900360200190206103ba929091611281565b5084846040516103cb9291906118de565b6040518091039020867fd8c9334b1a9c2f9da342a0a2b32629c1a229b6445dad78947f674b44444a75508787604051610405929190611917565b60405180910390a3505050505050565b60008281526003602090815260408083206001600160e01b0319851684529091528120546001600160a01b0316801561044f579050610362565b600061045a85610847565b90506001600160a01b03811661047557600092505050610362565b6040516301ffc9a760e01b602482015260009081906001600160a01b0384169060440160408051601f198184030181529181526020820180516001600160e01b03166301ffc9a760e01b179052516104cd919061192b565b600060405180830381855afa9150503d8060008114610508576040519150601f19603f3d011682016040523d82523d6000602084013e61050d565b606091505b5091509150811580610520575060208151105b8061054a575080601f8151811061053957610539611947565b01602001516001600160f81b031916155b1561055c576000945050505050610362565b6040516001600160e01b0319871660248201526001600160a01b0384169060440160408051601f198184030181529181526020820180516001600160e01b03166301ffc9a760e01b179052516105b2919061192b565b600060405180830381855afa9150503d80600081146105ed576040519150601f19603f3d011682016040523d82523d6000602084013e6105f2565b606091505b509092509050811580610606575060208151105b80610630575080601f8151811061061f5761061f611947565b01602001516001600160f81b031916155b15610642576000945050505050610362565b509095945050505050565b600082815260208190526040812060609060015b848111610748578085161580159061069157506000818152602083905260408120805461068d9061195d565b9050115b1561074057808260008381526020019081526020016000208080546106b59061195d565b80601f01602080910402602001604051908101604052809291908181526020018280546106e19061195d565b801561072e5780601f106107035761010080835404028352916020019161072e565b820191906000526020600020905b81548152906001019060200180831161071157829003601f168201915b50505050509050935093505050610761565b60011b610661565b5060006040518060200160405280600081525092509250505b9250929050565b8261077281610f23565b61077b57600080fd5b60408051808201825284815260208082018581526000888152600583528490209251835551600190920191909155815185815290810184905285917f1d6f5e03d3f63eb58751986629a5439baee5079ff04f345becb66e23eb154e4691015b60405180910390a250505050565b826107f281610f23565b6107fb57600080fd5b6000848152600260205260409020610814908484611281565b50837fe379c1624ed7e714cc0937528a32359d69d5281337765313dba4e081b72d757884846040516107da929190611917565b60008061085583603c610e50565b905080516000036108695750600092915050565b61087281611122565b9392505050565b606060066000858152602001908152602001600020838360405161089e9291906118de565b908152602001604051809103902080546108b79061195d565b80601f01602080910402602001604051908101604052809291908181526020018280546108e39061195d565b80156109305780601f1061090557610100808354040283529160200191610930565b820191906000526020600020905b81548152906001019060200180831161091357829003601f168201915b505050505090509392505050565b8361094881610f23565b61095157600080fd5b8361095d6001826119ad565b161561096857600080fd5b600085815260208181526040808320878452909152902061098a908484611281565b50604051849086907faa121bbeef5f32f5961a2a28966e769023910fc9479059ee3495d4c1a696efe390600090a35050505050565b60008181526004602052604090208054606091906109dc9061195d565b80601f0160208091040260200160405190810160405280929190818152602001828054610a089061195d565b8015610a555780601f10610a2a57610100808354040283529160200191610a55565b820191906000526020600020905b815481529060010190602001808311610a3857829003601f168201915b50505050509050919050565b82610a6b81610f23565b610a7457600080fd5b6000848152600460205260409020610a8d908484611281565b50837fb7d29e911041e8d9b843369e890bcb72c9388692ba48b65ac54e7214c4c348f784846040516107da929190611917565b82610aca81610f23565b610ad357600080fd5b837f65412581168e88a1e60c6459d7f44ae83ad0832e670826c05a4e2476b57af7528484604051610b0592919061152c565b60405180910390a2603c8303610b5c57837f52d7d861f09ab3d26239d492e8968629f95e9e318cf0b73bfddc441522a15fd2610b4084611122565b6040516001600160a01b03909116815260200160405180910390a25b600084815260016020908152604080832086845282529091208351610b8392850190611305565b5050505050565b6001600160a01b0382163303610bf85760405162461bcd60e51b815260206004820152602960248201527f455243313135353a2073657474696e6720617070726f76616c20737461747573604482015268103337b91039b2b63360b91b606482015260840160405180910390fd5b3360008181526007602090815260408083206001600160a01b03871680855290835292819020805460ff191686151590811790915590519081529192917f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31910160405180910390a35050565b60608167ffffffffffffffff811115610c7f57610c7f611644565b604051908082528060200260200182016040528015610cb257816020015b6060815260200190600190039081610c9d5790505b50905060005b82811015610d785760008030868685818110610cd657610cd6611947565b9050602002810190610ce891906119c4565b604051610cf69291906118de565b600060405180830381855af49150503d8060008114610d31576040519150601f19603f3d011682016040523d82523d6000602084013e610d36565b606091505b509150915081610d4557600080fd5b80848481518110610d5857610d58611947565b602002602001018190525050508080610d7090611a0b565b915050610cb8565b5092915050565b60008181526002602052604090208054606091906109dc9061195d565b81610da681610f23565b610daf57600080fd5b610dbe83603c61025a85611141565b505050565b82610dcd81610f23565b610dd657600080fd5b60008481526003602090815260408083206001600160e01b031987168085529083529281902080546001600160a01b0319166001600160a01b038716908117909155905190815286917f7c69f06bea0bdef565b709e93a147836b0063ba2dd89f02d0b7e8d931e6a6daa910160405180910390a350505050565b60008281526001602090815260408083208484529091529020805460609190610e789061195d565b80601f0160208091040260200160405190810160405280929190818152602001828054610ea49061195d565b8015610ef15780601f10610ec657610100808354040283529160200191610ef1565b820191906000526020600020905b815481529060010190602001808311610ed457829003601f168201915b5050505050905092915050565b60006001600160e01b03198216631674750f60e21b1480610362575061036282611171565b6000336001600160a01b037f0000000000000000000000000000000000000000000000000000000000000000161480610f845750336001600160a01b037f000000000000000000000000000000000000000000000000000000000000000016145b15610f9157506001919050565b6040516302571be360e01b8152600481018390526000907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316906302571be390602401602060405180830381865afa158015610ff9573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061101d9190611a24565b90507f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316816001600160a01b0316036110e4576040516331a9108f60e11b8152600481018490527f00000000000000000000000000000000000000000000000000000000000000006001600160a01b031690636352211e90602401602060405180830381865afa1580156110bd573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906110e19190611a24565b90505b6001600160a01b03811633148061087257506001600160a01b038116600090815260076020908152604080832033845290915290205460ff16610872565b6000815160141461113257600080fd5b5060200151600160601b900490565b604080516014808252818301909252606091602082018180368337505050600160601b9290920260208301525090565b60006001600160e01b0319821663c869023360e01b148061036257506103628260006001600160e01b0319821663691f343160e01b148061036257506103628260006001600160e01b031982166304928c6760e21b148061036257506103628260006001600160e01b0319821663bc1c58d160e01b148061036257506103628260006001600160e01b03198216631d9dabef60e11b148061122257506001600160e01b031982166378e5bf0360e11b145b8061036257506103628260006001600160e01b03198216631101d5ab60e11b148061036257506103628260006001600160e01b03198216631592ca1b60e31b148061036257506301ffc9a760e01b6001600160e01b0319831614610362565b82805461128d9061195d565b90600052602060002090601f0160209004810192826112af57600085556112f5565b82601f106112c85782800160ff198235161785556112f5565b828001600101855582156112f5579182015b828111156112f55782358255916020019190600101906112da565b50611301929150611379565b5090565b8280546113119061195d565b90600052602060002090601f01602090048101928261133357600085556112f5565b82601f1061134c57805160ff19168380011785556112f5565b828001600101855582156112f5579182015b828111156112f557825182559160200191906001019061135e565b5b80821115611301576000815560010161137a565b80356001600160e01b0319811681146113a657600080fd5b919050565b6000602082840312156113bd57600080fd5b6108728261138e565b60008083601f8401126113d857600080fd5b50813567ffffffffffffffff8111156113f057600080fd5b60208301915083602082850101111561076157600080fd5b60008060008060006060868803121561142057600080fd5b85359450602086013567ffffffffffffffff8082111561143f57600080fd5b61144b89838a016113c6565b9096509450604088013591508082111561146457600080fd5b50611471888289016113c6565b969995985093965092949392505050565b6000806040838503121561149557600080fd5b823591506114a56020840161138e565b90509250929050565b600080604083850312156114c157600080fd5b50508035926020909101359150565b60005b838110156114eb5781810151838201526020016114d3565b838111156114fa576000848401525b50505050565b600081518084526115188160208601602086016114d0565b601f01601f19169290920160200192915050565b8281526040602082015260006115456040830184611500565b949350505050565b60008060006060848603121561156257600080fd5b505081359360208301359350604090920135919050565b60008060006040848603121561158e57600080fd5b83359250602084013567ffffffffffffffff8111156115ac57600080fd5b6115b8868287016113c6565b9497909650939450505050565b6000602082840312156115d757600080fd5b5035919050565b6020815260006108726020830184611500565b6000806000806060858703121561160757600080fd5b8435935060208501359250604085013567ffffffffffffffff81111561162c57600080fd5b611638878288016113c6565b95989497509550505050565b634e487b7160e01b600052604160045260246000fd5b60008060006060848603121561166f57600080fd5b8335925060208401359150604084013567ffffffffffffffff8082111561169557600080fd5b818601915086601f8301126116a957600080fd5b8135818111156116bb576116bb611644565b604051601f8201601f19908116603f011681019083821181831017156116e3576116e3611644565b816040528281528960208487010111156116fc57600080fd5b8260208601602083013760006020848301015280955050505050509250925092565b6001600160a01b038116811461173357600080fd5b50565b6000806040838503121561174957600080fd5b82356117548161171e565b91506020830135801515811461176957600080fd5b809150509250929050565b6000806020838503121561178757600080fd5b823567ffffffffffffffff8082111561179f57600080fd5b818501915085601f8301126117b357600080fd5b8135818111156117c257600080fd5b8660208260051b85010111156117d757600080fd5b60209290920196919550909350505050565b6000602080830181845280855180835260408601915060408160051b870101925083870160005b8281101561183e57603f1988860301845261182c858351611500565b94509285019290850190600101611810565b5092979650505050505050565b6000806040838503121561185e57600080fd5b8235915060208301356117698161171e565b60008060006060848603121561188557600080fd5b833592506118956020850161138e565b915060408401356118a58161171e565b809150509250925092565b600080604083850312156118c357600080fd5b82356118ce8161171e565b915060208301356117698161171e565b8183823760009101908152919050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b6020815260006115456020830184866118ee565b6000825161193d8184602087016114d0565b9190910192915050565b634e487b7160e01b600052603260045260246000fd5b600181811c9082168061197157607f821691505b60208210810361199157634e487b7160e01b600052602260045260246000fd5b50919050565b634e487b7160e01b600052601160045260246000fd5b6000828210156119bf576119bf611997565b500390565b6000808335601e198436030181126119db57600080fd5b83018035915067ffffffffffffffff8211156119f657600080fd5b60200191503681900382131561076157600080fd5b600060018201611a1d57611a1d611997565b5060010190565b600060208284031215611a3657600080fd5b81516108728161171e56fea2646970667358221220e8496e72e0e4e6f46cf131fd00071a89198da0beb614f0ccc847a362b5829da864736f6c634300080d0033";

    public static final String FUNC_ABI = "ABI";

    public static final String FUNC_addr = "addr";

    public static final String FUNC_CONTENTHASH = "contenthash";

    public static final String FUNC_INTERFACEIMPLEMENTER = "interfaceImplementer";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_MULTICALL = "multicall";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_PUBKEY = "pubkey";

    public static final String FUNC_SETABI = "setABI";

    public static final String FUNC_setAddr = "setAddr";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SETCONTENTHASH = "setContenthash";

    public static final String FUNC_SETINTERFACE = "setInterface";

    public static final String FUNC_SETNAME = "setName";

    public static final String FUNC_SETPUBKEY = "setPubkey";

    public static final String FUNC_SETTEXT = "setText";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_TEXT = "text";

    public static final Event ABICHANGED_EVENT = new Event("ABIChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event ADDRCHANGED_EVENT = new Event("AddrChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>() {}));
    ;

    public static final Event ADDRESSCHANGED_EVENT = new Event("AddressChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event CONTENTHASHCHANGED_EVENT = new Event("ContenthashChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event INTERFACECHANGED_EVENT = new Event("InterfaceChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes4>(true) {}, new TypeReference<Address>() {}));
    ;

    public static final Event NAMECHANGED_EVENT = new Event("NameChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event PUBKEYCHANGED_EVENT = new Event("PubkeyChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event TEXTCHANGED_EVENT = new Event("TextChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected PublicResolver(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PublicResolver(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PublicResolver(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PublicResolver(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ABIChangedEventResponse> getABIChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ABICHANGED_EVENT, transactionReceipt);
        ArrayList<ABIChangedEventResponse> responses = new ArrayList<ABIChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ABIChangedEventResponse typedResponse = new ABIChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.contentType = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ABIChangedEventResponse> aBIChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ABIChangedEventResponse>() {
            @Override
            public ABIChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ABICHANGED_EVENT, log);
                ABIChangedEventResponse typedResponse = new ABIChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.contentType = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ABIChangedEventResponse> aBIChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ABICHANGED_EVENT));
        return aBIChangedEventFlowable(filter);
    }

    public List<AddrChangedEventResponse> getAddrChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADDRCHANGED_EVENT, transactionReceipt);
        ArrayList<AddrChangedEventResponse> responses = new ArrayList<AddrChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddrChangedEventResponse typedResponse = new AddrChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.a = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddrChangedEventResponse> addrChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AddrChangedEventResponse>() {
            @Override
            public AddrChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADDRCHANGED_EVENT, log);
                AddrChangedEventResponse typedResponse = new AddrChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.a = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AddrChangedEventResponse> addrChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDRCHANGED_EVENT));
        return addrChangedEventFlowable(filter);
    }

    public List<AddressChangedEventResponse> getAddressChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADDRESSCHANGED_EVENT, transactionReceipt);
        ArrayList<AddressChangedEventResponse> responses = new ArrayList<AddressChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddressChangedEventResponse typedResponse = new AddressChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.coinType = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newAddress = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddressChangedEventResponse> addressChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AddressChangedEventResponse>() {
            @Override
            public AddressChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADDRESSCHANGED_EVENT, log);
                AddressChangedEventResponse typedResponse = new AddressChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.coinType = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newAddress = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AddressChangedEventResponse> addressChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDRESSCHANGED_EVENT));
        return addressChangedEventFlowable(filter);
    }

    public List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalForAllEventResponse>() {
            @Override
            public ApprovalForAllEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVALFORALL_EVENT, log);
                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public List<ContenthashChangedEventResponse> getContenthashChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CONTENTHASHCHANGED_EVENT, transactionReceipt);
        ArrayList<ContenthashChangedEventResponse> responses = new ArrayList<ContenthashChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ContenthashChangedEventResponse typedResponse = new ContenthashChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ContenthashChangedEventResponse> contenthashChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ContenthashChangedEventResponse>() {
            @Override
            public ContenthashChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CONTENTHASHCHANGED_EVENT, log);
                ContenthashChangedEventResponse typedResponse = new ContenthashChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ContenthashChangedEventResponse> contenthashChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTENTHASHCHANGED_EVENT));
        return contenthashChangedEventFlowable(filter);
    }

    public List<InterfaceChangedEventResponse> getInterfaceChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(INTERFACECHANGED_EVENT, transactionReceipt);
        ArrayList<InterfaceChangedEventResponse> responses = new ArrayList<InterfaceChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InterfaceChangedEventResponse typedResponse = new InterfaceChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.interfaceID = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.implementer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<InterfaceChangedEventResponse> interfaceChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, InterfaceChangedEventResponse>() {
            @Override
            public InterfaceChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(INTERFACECHANGED_EVENT, log);
                InterfaceChangedEventResponse typedResponse = new InterfaceChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.interfaceID = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.implementer = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<InterfaceChangedEventResponse> interfaceChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INTERFACECHANGED_EVENT));
        return interfaceChangedEventFlowable(filter);
    }

    public List<NameChangedEventResponse> getNameChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NAMECHANGED_EVENT, transactionReceipt);
        ArrayList<NameChangedEventResponse> responses = new ArrayList<NameChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NameChangedEventResponse typedResponse = new NameChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NameChangedEventResponse> nameChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NameChangedEventResponse>() {
            @Override
            public NameChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NAMECHANGED_EVENT, log);
                NameChangedEventResponse typedResponse = new NameChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NameChangedEventResponse> nameChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NAMECHANGED_EVENT));
        return nameChangedEventFlowable(filter);
    }

    public List<PubkeyChangedEventResponse> getPubkeyChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PUBKEYCHANGED_EVENT, transactionReceipt);
        ArrayList<PubkeyChangedEventResponse> responses = new ArrayList<PubkeyChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PubkeyChangedEventResponse typedResponse = new PubkeyChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.x = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.y = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PubkeyChangedEventResponse> pubkeyChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PubkeyChangedEventResponse>() {
            @Override
            public PubkeyChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PUBKEYCHANGED_EVENT, log);
                PubkeyChangedEventResponse typedResponse = new PubkeyChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.x = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.y = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PubkeyChangedEventResponse> pubkeyChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PUBKEYCHANGED_EVENT));
        return pubkeyChangedEventFlowable(filter);
    }

    public List<TextChangedEventResponse> getTextChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TEXTCHANGED_EVENT, transactionReceipt);
        ArrayList<TextChangedEventResponse> responses = new ArrayList<TextChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TextChangedEventResponse typedResponse = new TextChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.indexedKey = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.key = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TextChangedEventResponse> textChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TextChangedEventResponse>() {
            @Override
            public TextChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TEXTCHANGED_EVENT, log);
                TextChangedEventResponse typedResponse = new TextChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.indexedKey = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.key = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TextChangedEventResponse> textChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TEXTCHANGED_EVENT));
        return textChangedEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple2<BigInteger, byte[]>> ABI(byte[] node, BigInteger contentTypes) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ABI, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.generated.Uint256(contentTypes)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
        return new RemoteFunctionCall<Tuple2<BigInteger, byte[]>>(function,
                new Callable<Tuple2<BigInteger, byte[]>>() {
                    @Override
                    public Tuple2<BigInteger, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, byte[]>(
                                (BigInteger) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> addr(byte[] node) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_addr, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> addr(byte[] node, BigInteger coinType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_addr, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.generated.Uint256(coinType)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> contenthash(byte[] node) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CONTENTHASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> interfaceImplementer(byte[] node, byte[] interfaceID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_INTERFACEIMPLEMENTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.generated.Bytes4(interfaceID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String account, String operator) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account), 
                new org.web3j.abi.datatypes.Address(160, operator)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> multicall(List<byte[]> data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MULTICALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(data, org.web3j.abi.datatypes.DynamicBytes.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name(byte[] node) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple2<byte[], byte[]>> pubkey(byte[] node) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PUBKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteFunctionCall<Tuple2<byte[], byte[]>>(function,
                new Callable<Tuple2<byte[], byte[]>>() {
                    @Override
                    public Tuple2<byte[], byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<byte[], byte[]>(
                                (byte[]) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setABI(byte[] node, BigInteger contentType, byte[] data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETABI, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.generated.Uint256(contentType), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setAddr(byte[] node, BigInteger coinType, byte[] a) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_setAddr, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.generated.Uint256(coinType), 
                new org.web3j.abi.datatypes.DynamicBytes(a)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setAddr(byte[] node, String a) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_setAddr, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.Address(160, a)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator, Boolean approved) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator), 
                new org.web3j.abi.datatypes.Bool(approved)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setContenthash(byte[] node, byte[] hash) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCONTENTHASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.DynamicBytes(hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setInterface(byte[] node, byte[] interfaceID, String implementer) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.generated.Bytes4(interfaceID), 
                new org.web3j.abi.datatypes.Address(160, implementer)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setName(byte[] node, String newName) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETNAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.Utf8String(newName)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPubkey(byte[] node, byte[] x, byte[] y) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETPUBKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.generated.Bytes32(x), 
                new org.web3j.abi.datatypes.generated.Bytes32(y)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setText(byte[] node, String key, String value) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETTEXT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.Utf8String(key), 
                new org.web3j.abi.datatypes.Utf8String(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> text(byte[] node, String key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TEXT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node), 
                new org.web3j.abi.datatypes.Utf8String(key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static PublicResolver load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PublicResolver(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PublicResolver load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PublicResolver(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PublicResolver load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PublicResolver(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PublicResolver load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PublicResolver(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PublicResolver> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _ens, String wrapperAddress, String _trustedETHController, String _trustedReverseRegistrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ens), 
                new org.web3j.abi.datatypes.Address(160, wrapperAddress), 
                new org.web3j.abi.datatypes.Address(160, _trustedETHController), 
                new org.web3j.abi.datatypes.Address(160, _trustedReverseRegistrar)));
        return deployRemoteCall(PublicResolver.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<PublicResolver> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _ens, String wrapperAddress, String _trustedETHController, String _trustedReverseRegistrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ens), 
                new org.web3j.abi.datatypes.Address(160, wrapperAddress), 
                new org.web3j.abi.datatypes.Address(160, _trustedETHController), 
                new org.web3j.abi.datatypes.Address(160, _trustedReverseRegistrar)));
        return deployRemoteCall(PublicResolver.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PublicResolver> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _ens, String wrapperAddress, String _trustedETHController, String _trustedReverseRegistrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ens), 
                new org.web3j.abi.datatypes.Address(160, wrapperAddress), 
                new org.web3j.abi.datatypes.Address(160, _trustedETHController), 
                new org.web3j.abi.datatypes.Address(160, _trustedReverseRegistrar)));
        return deployRemoteCall(PublicResolver.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PublicResolver> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _ens, String wrapperAddress, String _trustedETHController, String _trustedReverseRegistrar) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ens), 
                new org.web3j.abi.datatypes.Address(160, wrapperAddress), 
                new org.web3j.abi.datatypes.Address(160, _trustedETHController), 
                new org.web3j.abi.datatypes.Address(160, _trustedReverseRegistrar)));
        return deployRemoteCall(PublicResolver.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ABIChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public BigInteger contentType;
    }

    public static class AddrChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public String a;
    }

    public static class AddressChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public BigInteger coinType;

        public byte[] newAddress;
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String owner;

        public String operator;

        public Boolean approved;
    }

    public static class ContenthashChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public byte[] hash;
    }

    public static class InterfaceChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public byte[] interfaceID;

        public String implementer;
    }

    public static class NameChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public String name;
    }

    public static class PubkeyChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public byte[] x;

        public byte[] y;
    }

    public static class TextChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public byte[] indexedKey;

        public String key;
    }
}
