#!/bin/bash

DockerCategory=airta
NAME=airgent
Release=1.0
timestamp=$(date +"%Y%m%d%H.%s")
ImageGlobalTag=${Release}-${timestamp}

mvn clean
mvn package

docker build -t ${DockerCategory}/${NAME}:${ImageGlobalTag} -f Dockerfile . || exit 1