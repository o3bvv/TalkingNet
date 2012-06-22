#!/bin/sh

mvn install:install-file -Dfile=./bcprov-jdk15on-147.jar -DgroupId=org.bouncycastle -DartifactId=provider  -Dversion=1.47 -Dpackaging=jar 
