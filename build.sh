#!/bin/bash

DockerCategory=airta
NAME=airgent
Release=1.0
timestamp=$(date +"%Y%m%d%H.%s")
ImageGlobalTag=${Release}-${timestamp}

./mvnw clean
./mvnw package -DskipTests

docker pull airta/agent_base_publish:latest
docker build --no-cache -t ${DockerCategory}/${NAME}:${ImageGlobalTag} -f Dockerfile . || exit 1
docker tag ${DockerCategory}/${NAME}:${ImageGlobalTag} ${DockerCategory}/${NAME}:latest
docker push ${DockerCategory}/${NAME}:${ImageGlobalTag}
docker push ${DockerCategory}/${NAME}:latest