#!/bin/bash
#
# ===============================
# Build base image with dynamically tag generated.
# IMPORTANT: don't manually modify the tag in each image layer.
#
# Author: Allen Yin.
# Date: 2019-10-11.
# ===============================

# Define global configs.
DockerCategory=airta
NAME=agent_base
Release=2.1
timestamp=$(date +"%m%d%H.%s")
ImageGlobalTag=${Release}-${timestamp}

# Define layer names.
Base_init=agent_base_0_platform
Base_graph=agent_base_1_graph
Base_browser=agent_base_2_browser
Base_devtool=agent_base_3_devtool
Base_publish=agent_base_publish

# Define layer folder names.
Folder_init=00.base
Folder_graph=01.vnc_graph
Folder_browser=02.browser
Folder_devtool=03.devtool
Folder_publish=04.publish

function main() {

  echo "build all with default ${ImageGlobalTag}"
  build_all
  release_version
  print_all
#  clearLocal_version
}

# build entry for overall.
function build_all() {

  dockerBuild ${Folder_init} ${Base_init}
  buildImage ${Folder_graph} ${Base_graph}
  buildImage ${Folder_browser} ${Base_browser}
  buildImage ${Folder_devtool} ${Base_devtool}
  buildImage_publish ${Folder_publish} ${Base_publish}

}

function dockerBuild() {

  echo "Building image layer: $2 with tag: ${ImageGlobalTag}"
  cd $1 || exit
  docker build -t ${DockerCategory}/$2:${ImageGlobalTag} -f Dockerfile . || exit 1
  cd ..
  echo "Done Building image $2:${ImageGlobalTag}"
}

function dockerBuild_publish() {

  echo "Building image layer: $2 with tag: ${ImageGlobalTag}"
  cd $1 || exit
  docker build -t ${DockerCategory}/$2:latest -f Dockerfile . || exit 1
  cd ..
  echo "Done Building image $2:${ImageGlobalTag}"
}

function buildImage() {

  assignImageTag $1
  dockerBuild $1 $2
}

function buildImage_publish() {

  assignImageTag $1
  dockerBuild_publish $1 $2
}

# dynamic change the layer versions.
function assignImageTag() {

  cd "$1" || exit
  rm Dockerfile
  cat Dockerfile_template > Dockerfile
  sed -i 's/{{ versionPlaceHolder }}/'${ImageGlobalTag}'/g' Dockerfile
#  sed -i '' 's/{{ versionPlaceHolder }}/'${ImageGlobalTag}'/g' Dockerfile
  cd ..
  echo "layer tag assigned .."
}

function release_version() {

  echo "publishing base images .."
  docker push ${DockerCategory}/${Base_init}:${ImageGlobalTag}
  docker push ${DockerCategory}/${Base_graph}:${ImageGlobalTag}
  docker push ${DockerCategory}/${Base_browser}:${ImageGlobalTag}
  docker push ${DockerCategory}/${Base_devtool}:${ImageGlobalTag}
  docker push ${DockerCategory}/${Base_publish}:latest
}

function clearLocal_version() {
  echo "clearing local image caches .."
  docker rm -f ${DockerCategory}/${Base_init}:${ImageGlobalTag}
  docker rm -f ${DockerCategory}/${Base_graph}:${ImageGlobalTag}
  docker rm -f ${DockerCategory}/${Base_browser}:${ImageGlobalTag}
  docker rm -f ${DockerCategory}/${Base_devtool}:${ImageGlobalTag}
  docker rm -f ${DockerCategory}/${Base_publish}:latest
}

function print_all() {

    echo ""
    echo "==================================================="
    echo "==================publish info====================="
    echo "==="${DockerCategory}/${Base_init}:${ImageGlobalTag}"==="
    echo "==="${DockerCategory}/${Base_graph}:${ImageGlobalTag}"==="
    echo "==="${DockerCategory}/${Base_browser}:${ImageGlobalTag}"==="
    echo "==="${DockerCategory}/${Base_devtool}:${ImageGlobalTag}"==="
    echo "==="${DockerCategory}/${Base_publish}:latest"==="
    echo "==================================================="
    echo ""
}

main
