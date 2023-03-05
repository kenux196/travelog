#!/bin/bash

build_frontend () {
    echo "build frontend-vue3-vite"
    rm -rf  src/main/resources/static
    cd frontend-vue3-vite
    npm run build
    cd ..
}

build_backend () {
    echo "build backend"
    ./gradlew build -x test
}

build_all () {
    echo "all build"
    build_frontend
    build_backend
}

show_usage () {
    echo "Usage: $0 param1"
    echo "param1 : 'all', 'back', 'front'"
    exit -1
}

deploy () {
    echo "Copy jar file..."
    cp build/libs/travelog-0.0.1-SNAPSHOT.jar ./app.jar

    echo "오라클 인스턴스로 업로드 시작"
    scp ./app.jar web-app-runner.sh ubuntu@kenux.duckdns.org:/home/ubuntu/web
    rm app.jar

    echo "배포 완료. 서버에 접속해서 앱 재실행하세요..."
}

echo "Project deploy manager v0.0.1"
if [ $# -ne 1 ]
then
    show_usage
else
    if [ $1 ==  "back" ]; then build_backend
    elif [ $1 ==  "front" ]; then build_frontend
    elif [ $1 ==  "all" ]; then build_all
    else
        echo "Wrong parameter~~"
        show_usage
    fi
    deploy
fi
