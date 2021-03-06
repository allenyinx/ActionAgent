# ActionAgent
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.springframework.boot/spring-boot-starter-parent/badge.svg)](https://search.maven.org/artifact/org.springframework.boot/spring-boot-starter-parent)
[![Docker Hub](https://img.shields.io/docker/pulls/airta/airgent.svg?style=flat)](https://cloud.docker.com/u/airta/repository/docker/airta/airgent/)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/allenyinx/ActionAgent.svg)](http://isitmaintained.com/project/allenyinx/ActionAgent "Average time to resolve an issue")
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=allenyinx_ActionAgent&metric=bugs)](https://sonarcloud.io/dashboard?id=allenyinx_ActionAgent)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=allenyinx_ActionAgent&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=allenyinx_ActionAgent)
[![Image](https://images.microbadger.com/badges/image/airta/airgent.svg)](https://microbadger.com/images/airta/airgent)

<p align="left">
    <a href="https://codecov.io/gh/allenyinx/ActionAgent"><img src="https://codecov.io/gh/allenyinx/ActionAgent/branch/develop/graph/badge.svg" /></a>
    <a href='https://circleci.com/gh/allenyinx/ActionAgent/tree/develop'><img src='https://circleci.com/gh/allenyinx/ActionAgent/tree/develop.svg?style=svg'></a>
    <a href='https://sonarcloud.io/dashboard?id=allenyinx_ActionAgent'><img src='https://sonarcloud.io/api/project_badges/measure?project=allenyinx_ActionAgent&metric=alert_status'></a>
    <a href='https://travis-ci.org/allenyinx/ActionAgent'><img src='https://travis-ci.org/allenyinx/ActionAgent.svg?branch=develop'></a>
    <a href='http://52.175.51.58:8080/job/ActionAgent_APP/'><img src='http://52.175.51.58:8080/buildStatus/icon?job=ActionAgent_APP'></a>
    </p>

# airgent
webdriver agent


## Features
* Crawler for deep links
* Headless Chromium
* WebDriver
* Report
* Kafka message consumer
* Screenshots
* Log, reports, console info
* Produce kafka message to sitemap
* Parse and exec kinds of actions

Install
=======

The quick way::

    docker run -p 5900:5900 airta/airgent:latest

