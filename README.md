# SHOP_API

Spring Boot 기반 쇼핑몰 REST API입니다. 상품 탐색부터 회원 인증, 장바구니, 주문까지의 구매 흐름과 관리자용 관리 API를 제공합니다.

## 주요 기능

- 회원 가입 및 내 정보 조회
- JWT 기반 로그인과 Argon2 비밀번호 암호화
- 상품 목록·상세 조회 및 카테고리 조회
- 장바구니 조회와 항목 관리
- 주문 생성 및 주문 내역 조회
- 관리자용 회원·상품·카테고리·주문 관리 API

## 기술 스택

| 구분 | 기술 |
| --- | --- |
| 언어 | Java 17 |
| 프레임워크 | Spring Boot 3.2.5, Spring Web, Spring Security |
| 데이터 접근 | Spring Data JPA |
| 데이터베이스 | PostgreSQL, H2 의존성 포함 |
| 인증 | java-jwt 4.4.0, Argon2 |
| 빌드·테스트 | Gradle Wrapper, JUnit Platform, Spring Boot Test |

## 실행 방법

Java 17과 PostgreSQL이 필요합니다.

```bash
git clone https://github.com/PracLee/SHOP_API.git
cd SHOP_API
```

`src/main/resources/application.yml`의 데이터베이스 연결 정보를 실행 환경에 맞게 설정합니다. 기본 연결 대상은 `localhost:5432/postgres`입니다. JWT 및 PortOne 설정도 환경에 맞게 지정합니다. 환경변수로 덮어쓸 수 있습니다.

```bash
export SPRING_DATASOURCE_URL='jdbc:postgresql://localhost:5432/postgres'
export SPRING_DATASOURCE_USERNAME='your_db_user'
export SPRING_DATASOURCE_PASSWORD='your_db_password'
export JWT_SECRET='replace-with-your-own-secret'
export PORTONE_REST_API_KEY='your_api_key'
export PORTONE_REST_API_SECRET='your_api_secret'
bash gradlew bootRun
```

기본 서버 주소는 `http://localhost:8080`입니다. 데이터베이스 테이블은 JPA 엔티티에 맞게 사전 준비해야 합니다. 현재 설정에는 스키마 자동 생성 옵션이 명시되어 있지 않습니다.

## API 구성

| 경로 | 용도 |
| --- | --- |
| `/users`, `/users/me` | 회원 가입 및 내 정보 |
| `/session` | 사용자 로그인 |
| `/products`, `/products/{id}` | 상품 조회 |
| `/categories` | 카테고리 조회 |
| `/cart`, `/cart/line-items` | 장바구니 및 항목 관리 |
| `/orders`, `/orders/{id}` | 주문 |
| `/admin/session` | 관리자 로그인 |
| `/admin/users`, `/admin/products` | 관리자 회원·상품 관리 |
| `/admin/categories`, `/admin/orders` | 관리자 카테고리·주문 관리 |

회원 가입, 로그인, 상품·카테고리 조회 외의 일반 API는 인증이 필요합니다. 관리자 권한과 요청 본문은 각 컨트롤러 및 DTO를 참고하세요.

## 프로젝트 구조

```text
src/main/java/com/shop/shop/
├── controllers/  # 사용자 및 관리자 API
├── security/     # 인증과 보안 설정
└── backdoor/     # 개발용 API
src/main/resources/application.yml
src/test/java/com/shop/shop/
```

## 빌드 및 테스트

```bash
bash gradlew test
bash gradlew build
```

데이터베이스 및 외부 연동이 필요한 경우 해당 환경을 먼저 준비하세요. `/backdoor/**`는 현재 보안 설정에서 GET 요청을 공개하므로 배포 전에 개발용 API 노출 여부를 확인해야 합니다.
