# 마트.. 다녀오셨어요? 포팅 매뉴얼

## 📌 Project Skill Stack Version


| SKill | Version|
|---|---|
| Java | 17 |
| Spring Boot | 3.2.5|
| MariaDB | 11.3.2 |
| Redis | 7.2.4 |
| Node.js | 18 |
| React | 18.2.0 |


## 📌 EC2 포트 번호
| Skill | 	Port   |
|---|---------|
|Back | 	8080   |
|MariaDB | 	3306   | 
|Redis | 	6379   | 
|Nginx | 	80/443 | 
| Prometheus| 8081 |
| Grafana | 8082 | 


## 📌 외부 프로그램

- Kakao 로그인

- Naver 로그인

- Naver Clova OCR




## 📌 빌드 방법

### 백엔드

secret.yml 파일과 data.sql 파일을 생성하고 src/main/resources 에 위치하게 해줍니다.

**secret.yml**
```yml

spring:
  security:
    oauth2:
      client:
        registration:
          naver:
            client-id: <Naver 개발자 센터에서 확인>
            client-secret: <Naver 개발자 센터에서 확인>
            client-authentication-method: client_secret_post
            authorization-grant-type: authorization_code
            redirect-uri: <Naver 개발자 센터에 작성한 값 등록>
            client-name: Naver
          kakao:
            client-id: <Kakao 개발자 센터에서 확인>
            client-secret: <Kakao 개발자 센터에서 확인>
            client-authentication-method: client_secret_post
            authorization-grant-type: authorization_code
            scope: # https://developers.kakao.com/docs/latest/ko/kakaologin/common#user-info
              - account_email
            redirect-uri: <Kakao 개발자 센터에 작성한 값 등록>
            client-name: Kakao
        provider:
          naver:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token-uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-info-authentication-method: header
            user-name-attribute: response # Naver 응답 값 resultCode, message, response 중 response 지정
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-info-authentication-method: header
            user-name-attribute: id # Kakao 응답 값 id, connected_at, properties, kakao_account 중 id 지정

secret:
  jwt:
    access-token-ttl: <원하는 access-token의 만료 시간 작성>
    refresh-token-ttl: <원하는 refresh-token의 만료 시간 작성>
    secret-key: <원하는 JWT의 시크릿 키 작성>
    user-id-claim-key: <원하는 JWT의 userId를 담을 키 작성>
  crawl:
    base-url: https://gs1.koreannet.or.kr/pr/
    product-name-selector: ".pv_title > h3"
    kan-code-name-selector: ".pv_info > tbody > tr > td"
  ocr:
    general:
      service-key: <네이버 CLOVA General OCR 서비스 키 입력>
      api-url: <네이버 CLOVA General OCR API-URL 입력>
    document:
      service-key: <네이버 CLOVA Document OCR 서비스 키 입력>
      api-url: <네이버 CLOVA Document OCR API-URL 입력>
  sse:
    ttl: <SSE 객체 유지 시간 작성>
  exp:
    eaten-amount: 20
    register-amount: 10
    got-badge-amount: 30
  redirect:
    login-fail: <개발 환경 서버의 호스트 작성>/api/users/login-fail

firebase:
  key:
    path: /firebase/haveyoubeenmart-firebase-adminsdk-hhvzo-c86637d5d2.json

---
spring:
  config:
    activate:
      on-profile: local
  security:
    oauth2:
      client:
        registration:
          naver:
            redirect-uri: "{baseUrl}/{action}/oauth2/code/{registrationId}"
          kakao:
            redirect-uri: "{baseUrl}/{action}/oauth2/code/{registrationId}"

secret:
  redirect:
    login-fail: /api/users/login-fail


```

**data.sql**

data.sql 파일은 내용이 많기 떄문에 아래 링크를 접속한 후 다운로드 받으거나 현재 폴더에 첨부된 data.sql 파일을 다운로드 받아주세요.

[프로젝트 SQL 모음](https://west-lantana-35c.notion.site/SQL-659263d4830a454ba496f02c1e361e14?pvs=4)


해당 파일들을 세팅한 후 `./gradlew build` 명령을 입력하면 빌드가 가능합니다.

빠르게 프로젝트를 실행시키고 싶다면

`docker pull kgh2120/hybm-backend` 한 후 run을 시키면 됩니다.

---

### 프론트엔드

폴더 내에 있는 `.env`와 `.env.prod` 를 다음과 같이 수정해주시면 됩니다.

**.env**
```
VITE_API_DEV=<서버 주소>/api
VITE_REDIRECT_URI_BASE=http://localhost:5173/auth
VITE_RELOGIN_URI_BASE=http://localhost:5173/landing

# firebase
VITE_FIREBASE_API_KEY=<파이어베이스 API KEY>
VITE_FIREBASE_AUTH_DOMAIN=<파이어 베이스 AUTH DOMAIN>
VITE_FIREBASE_PROJECT_ID=<파이어 베이스 Project ID>
VITE_FIREBASE_STORAGE_BUCKET=<파이어 베이스 STORAGE BUCKET>
VITE_FIREBASE_MESSAGING_SENDER_ID=<파이어 베이스 STORAGE BUCKET>
VITE_FIREBASE_APP_ID=<파이어 베이스 STORAGE BUCKET>
VITE_FIREBASE_MEASUREMENT_ID=<파이어 베이스 STORAGE BUCKET>
VITE_FIREBASE_VAPID_KEY=<파이어 베이스 STORAGE BUCKET>

```

**.env.prod**

```
VITE_API_DEV=<서버 주소>/api
VITE_REDIRECT_URI_BASE=<서버 주소>/auth
VITE_RELOGIN_URI_BASE=<서버 주소>/landing

# firebase
VITE_FIREBASE_API_KEY=<파이어베이스 API KEY>
VITE_FIREBASE_AUTH_DOMAIN=<파이어 베이스 AUTH DOMAIN>
VITE_FIREBASE_PROJECT_ID=<파이어 베이스 Project ID>
VITE_FIREBASE_STORAGE_BUCKET=<파이어 베이스 STORAGE BUCKET>
VITE_FIREBASE_MESSAGING_SENDER_ID=<파이어 베이스 STORAGE BUCKET>
VITE_FIREBASE_APP_ID=<파이어 베이스 STORAGE BUCKET>
VITE_FIREBASE_MEASUREMENT_ID=<파이어 베이스 STORAGE BUCKET>
VITE_FIREBASE_VAPID_KEY=<파이어 베이스 STORAGE BUCKET>

```

다음 파일을 설정해준 후 아래 명령어를 실행시키면 빌드가 가능합니다. 이때 npm과 yarn을 설치해야 합니다.

```
yarn install
yarn build --mode prod
```

빌드 파일을 실행시키려면 nginx와 같은 웹서버와 함께 이용해야 합니다.

도커로 빌드하고 싶다면 `docker build -t <파일이름> .` 을 입력하시면 nginx와 함께 바로 이용할 수 있습니다.

이미 즉시 실행시키고 싶다면 `docker pull kgh2120/hybm-frontend` 를 한 후 run 시키면 됩니다.

---

### Flutter

`/final_project/lib/screen/home_screen.dart` 파일의 48번 라인을 다음과 같이 수정한다.

`initialUrl: '<서버주소>'`
 
---

### Nginx 구성

nginx를 실행시키기 위해선 다음과 같은 준비를 해주시면 됩니다.

**docker-compose.yml**
```yml
version: '3.9'
services:
  nginx:
    container_name: nginx
    image: nginx:latest
    volumes:
      - ./config/nginx.conf:/etc/nginx/nginx.conf
      - ./8D9CD1487A4984D5D72ECEDEDDA7C401.txt:/usr/share/nginx/html/.well-known/pki-validation/8D9CD1487A4984D5D72ECEDEDDA7C401.txt
      - ./ssl:/etc/nginx/ssl
    network_mode: "host"
networks:
  host:
    external: true

```

**config/nginx.conf**

```
#user  nginx;
worker_processes  auto;

error_log  /var/log/nginx/error.log notice;
pid        /var/run/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    keepalive_timeout  65;

    #gzip  on;

    server {
        listen       80;
        listen  [::]:80;
        server_name  k10a707.p.ssafy.io;
        client_max_body_size 100M;


        location / {
           return 301 https://$host$request_uri;
        }


        location /.well-known/pki-validation/ {
            alias /usr/share/nginx/html/.well-known/pki-validation/;
        }

    }


        server {

        listen  443 ssl;
        server_name k10a707.p.ssafy.io;
        client_max_body_size 100M;

        ssl_certificate /etc/nginx/ssl/certificate.crt;
        ssl_certificate_key /etc/nginx/ssl/private.key;

         location / {
                proxy_pass http://localhost:8090/;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_http_version 1.1;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
        }
        location /api/ {
                proxy_pass http://localhost:8080/;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_http_version 1.1;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
        }
        location /board/ {
                proxy_pass http://localhost:8082/;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_http_version 1.1;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
        }
        location /connections/datasources/new {
                proxy_pass http://localhost:8082/connections/datasources/new;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_http_version 1.1;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
        }


        }


}

```


**ssl/***

ssl 등록을 위해 다음과 같은 작업을 진행해야 합니다.
이 프로젝트에선 zeroSSL이라는 서비스를 이용해 Https 인증을 진행했습니다.

1. ZeroSSL 회원가입
2. ZeroSSL 에서 전달받은 파일을 nginx에 넣은 후 ZeroSSL에서 인증을 받음(해당 파일은 docker-compose.yml과 동일한 위치에 두고 nginx를 실행시키면 된다.)
3. 인증을 받은 후 zeroSSL에서 제공하는 파일을 다운로드 받은 후 압축을 해제한 다음 `cat certificate.crt ca_bundle.crt >> certificate.crt ` 명령을 입력한 후 해당 파일들을 ssl 폴더에 위치하게 해준다.


---

### 모니터링 툴

모니터링 툴로는 프로메테우스와 그라파나를 이용했습니다.
**docker-compose.yml**

```yml
version: '3.7'

services:
  prometheus:
    image: prom/prometheus
    container_name: prometheus
    volumes:
      - ./prometheus.yml:/prometheus/prometheus.yml:ro
    ports:
      - 8081:9090
    command:
      - '--web.enable-lifecycle'
    restart: always
    networks:
      - promnet

  grafana:
    image: grafana/grafana
    container_name: grafana
    volumes:
      - ./grafana-volume:/var/lib/grafana
      - ./grafana/config/grafana-init.ini:/etc/grafana/grafana.ini
    restart: always
    networks:
      - promnet
    ports:
      - 8082:3000
    user: root

networks:
  promnet:
    driver: bridge
```

**prometheus.yml**

```yml
# default 값 설정하기 - 여기 부분은 전부 설정 안해줘도 상관없음
global:
  scrape_interval: 15s # scrap target의 기본 interval을 15초로 변경 / default = 1m
  scrape_timeout: 15s # scrap request 가 timeout 나는 길이 / default = 10s

scrape_configs:
  - job_name: 'monitoring-production' # job_name 은 모든 scrap 내에서 고유해야함
    scrape_interval: 10s # global에서 default 값을 정의해주었기 떄문에 안써도됨
    scrape_timeout: 10s # global에서 default 값을 정의해주었기 떄문에 안써도됨
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['k10a707.p.ssafy.io:8080']
```

