#!/bin/bash

echo "Gradle Build..."
./gradlew build
echo "Copy jar file..."
cp build/libs/travelog-0.0.1-SNAPSHOT.jar ./app.jar
echo "오라클 인스턴스로 업로드 시작"
scp ./app.jar ubuntu@kenux.duckdns.org:/home/ubuntu/web
rm app.jar
echo "배포 완료. 서버에 접속해서 앱 재실행하세요..."
