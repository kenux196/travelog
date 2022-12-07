#!/bin/bash

processId=$(lsof -t -i:8080)
echo "Kill web app process $processId"
kill -TERM "$processId"

echo "Waiting 3 seconds."
sleep 3

active='test'
if [ -n "$1" ]
then
    active=$1
fi
echo "Run web app => active: $active"
nohup java -jar app.jar --spring.profiles.active="$active" &
