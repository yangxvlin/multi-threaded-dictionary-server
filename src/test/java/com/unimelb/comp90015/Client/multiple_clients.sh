#!/usr/bin/env bash

#DIR = "../../../../../../../output/artifacts"

java -Duser.language=en -jar "../../../../../../../output/artifacts/DictionaryServer/DictionaryServer.jar" 49153 dictionary.json 1 60 &

counter=1
until [ $counter -gt 3 ]
do
    echo ${counter}
    java -Duser.language=en -jar "../../../../../../../output/artifacts/DictionaryClient/DictionaryClient.jar" 127.0.0.1 49153 ${counter} &
    ((counter++))
done

echo All clients created
