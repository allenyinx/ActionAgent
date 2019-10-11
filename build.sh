#!/bin/bash

DockerCategory=airta
NAME=airgent
Release=1.0
timestamp=$(date +"%Y%m%d%H.%s")
ImageGlobalTag=${Release}-${timestamp}

./mvnw clean
./mvnw package

docker build -t ${DockerCategory}/${NAME}:${ImageGlobalTag} -f Dockerfile . || exit 1
docker push ${DockerCategory}/${NAME}:${ImageGlobalTag}