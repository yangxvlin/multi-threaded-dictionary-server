#!/usr/bin/env bash

#DIR = "../../../../../../../output/artifacts"

java -Duser.language=en -jar "../../../../../../../output/artifacts/DictionaryServer/DictionaryServer.jar" 49153 dictionary.json 2 60 3 &

counter=1
n_clients=7
until [[ ${counter} -gt ${n_clients} ]]
do
    echo ${counter}
    java -Duser.language=en -jar "../../../../../../../output/artifacts/DictionaryClient/DictionaryClient.jar" 127.0.0.1 49153 ${counter} &
    ((counter++))
done

echo All clients created
