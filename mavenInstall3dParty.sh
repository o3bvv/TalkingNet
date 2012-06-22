#!/bin/sh

START_DIR=`pwd`
SCRIPT_NAME="mavenInstall.sh"

for i in `find ./ -type f -name $SCRIPT_NAME`
do  
  cd `echo $i | sed "s/$SCRIPT_NAME//g"`
  sh $SCRIPT_NAME
  cd $START_DIR
done
