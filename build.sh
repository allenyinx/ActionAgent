#!/bin/bash

mvn clean
mvn package

docker build -t airta/airgent:1.0 .