FROM --platform=linux/amd64 openjdk:17-slim AS builder

WORKDIR /app

COPY . .

RUN ./mvnw clean compile install package

FROM --platform=linux/amd64 openjdk:17-alpine AS runner

WORKDIR /app

COPY --from=builder /app/target/*.jar  ./core.jar
COPY --from=builder /app/.env  ./.env

CMD ["java", "-jar", "core.jar"]
