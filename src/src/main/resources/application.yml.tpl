server:
  port: _MICROSERVICE_PORT_
spring:
  application:
    name: _MICROSERVICE_
eureka:
  instance:
    leaseRenewalIntervalInSeconds: 10
    leaseExpirationDurationInSeconds: 11
    ip-address: _MICROSERVICE_ACCESS_IP_
    prefer-ip-address: true
  client:
    serviceUrl:
      defaultZone: http://_EUREKA_VHOST_:_EUREKA_PORT_/eureka/
    healthcheck:
      enabled: true
    lease: null
duration: 5
logging:
  file: logs/${spring.application.name}.log
  level:
    org.springframework.cloud: DEBUG
