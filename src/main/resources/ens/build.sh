#!/usr/bin/env bash

set -e
set -o pipefail

targets="
./minimum-contracts/PublicResolver
./minimum-contracts/ENSRegistryWithFallback
"

for target in ${targets}; do
    dirName=$(dirname $target)
    fileName=$(basename $target)

    cd $dirName
    echo "Compiling Solidity file ${fileName}.sol:"
    solc --bin --abi --optimize --overwrite ${fileName}.sol -o ./build/
    echo "Complete"

    echo "Generating web3j bindings"
    web3j generate solidity \
        -b build/${fileName}.bin \
        -a build/${fileName}.abi \
        -p xyz.seleya.ethereum.ens.contracts.generated \
        -o ../../../../main/java/ > /dev/null
    echo "Complete"

    cd -
done
