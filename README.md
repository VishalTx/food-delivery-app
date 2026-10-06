# QuickFood Spring Boot Microservices

This workspace contains five independently runnable Spring Boot services. Each service has its own Maven project, application entry point, API, service, repository, model, resources, and test source tree.

## Services

| Service | Default port | Java package |
| --- | ---: | --- |
| user-service | 8081 | `com.quickfood.userservice` |
| restaurant-service | 8082 | `com.quickfood.restaurantservice` |
| order-service | 8083 | `com.quickfood.orderservice` |
| payment-service | 8084 | `com.quickfood.paymentservice` |
| delivery-service | 8085 | `com.quickfood.deliveryservice` |

Run a service from the repository root with `mvn -f user-service/pom.xml spring-boot:run`, replacing `user-service` with the service to run. Run its tests with `mvn -f user-service/pom.xml test`.

The H2 in-memory datasource and CRUD endpoints are development templates. Replace them with the service's production database, validation, authentication, and inter-service messaging before deployment.