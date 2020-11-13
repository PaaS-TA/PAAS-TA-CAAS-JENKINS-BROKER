# PAAS-TA-CAAS-JENKINS-BROKER

PaaS-TA 에서 제공하는 Jenkins 서비스 브로커로 클라우드 컨트롤러와 서비스 브로커간의 v2 서비스 API 를 제공합니다.

- [시작하기](#시작하기)
  - [PAAS-TA-CAAS-JENKINS-BROKER 설치 방법](#PAAS-TA-CAAS-JENKINS-BROKER-설치-방법)
  - [PAAS-TA-CAAS-JENKINS-BROKER 빌드 방법](#PAAS-TA-CAAS-JENKINS-BROKER-빌드-방법)
- [개발 환경](#개발-환경)

## 시작하기

PAAS-TA-CAAS-JENKINS-BROKER가 수행하는 서비스 관리 작업은 다음과 같습니다.
- Catalog : PAAS-TA-CAAS-JENKINS-BROKER 카탈로그 조회
- Provisioning : PAAS-TA-CAAS-JENKINS-BROKER 인스턴스 생성 ( parameters "owner", "org_name" 필수 )
- Updateprovisioning : PAAS-TA-CAAS-JENKINS-BROKER 인스턴스 갱신
- Deprovisioning : PAAS-TA-CAAS-JENKINS-BROKER 인스턴스 삭제

### PAAS-TA-CAAS-JENKINS-BROKER 설치 방법

[서비스팩 설치 가이드](https://github.com/PaaS-TA/Guide-5.0-Ravioli/blob/master/service-guide/tools/PAAS-TA_CONTAINER_SERVICE_INSTALL_GUIDE_V2.0.md)의 가이드를 참고하시면 아키텍쳐와 설치 및 사용법에 대해 자세히 알 수 있습니다.

### PAAS-TA-CAAS-JENKINS-BROKER 빌드 방법

PAAS-TA-CAAS-JENKINS-BROKER 소스 코드를 활용하여 로컬 환경에서 빌드하고 싶을 때 다음 명령어를 입력합니다.
```
$ gradle build
```

## 개발 환경

PAAS-TA-CAAS-JENKINS-BROKER의 개발 환경은 다음과 같습니다.

| Situation                      | Version |
| ------------------------------ | ------- |
| JDK                            | 8       |
| Gradle                         | 4.4.1   |
| Spring Boot                    | 1.5.10  |
| Spring Boot Cf Service Broker  | 2.4.0   |
| Hibernate Validator            | 5.1.0   |
| Json Path                      | 0.9.1   |
| Jacoco                         | 0.7.9+  |

## 라이선스

[Apache-2.0 License](http://www.apache.org/licenses/LICENSE-2.0)
