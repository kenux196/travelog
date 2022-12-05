#!/bin/bash

echo "빌드 시작"
./gradlew build

echo "빌드 완료"


echo "오라클 인스턴스로 업로드 시작"
scp build/libs/travelog-0.0.1-SNAPSHOT.jar ubuntu@129.154.50.38:/home/ubuntu
echo "업로드 완료"
