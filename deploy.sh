#!/bin/bash

all='all'
if [ -n "$1" ]
then
    echo "Build Front-End"
    cd frontend
    npm run build
    cd ..
fi

echo "Build Back-End"
./gradlew build -x test

echo "Copy jar file..."
cp build/libs/travelog-0.0.1-SNAPSHOT.jar ./app.jar

echo "오라클 인스턴스로 업로드 시작"
scp ./app.jar web-app-runner.sh ubuntu@kenux.duckdns.org:/home/ubuntu/web
rm app.jar

echo "배포 완료. 서버에 접속해서 앱 재실행하세요..."
