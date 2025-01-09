# Maven 構建
FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /app

# 複製專案檔案
COPY pom.xml .
COPY src ./src

# 執行 Maven Package
RUN mvn clean package -DskipTests

# 第二階段：運行 Spring Boot 應用
FROM amazoncorretto:17
WORKDIR /app

# 複製 JAR 檔案到運行階段
COPY --from=build /app/target/*.jar app.jar

# 暴露應用埠
EXPOSE 8080

# 啟動應用
ENTRYPOINT ["java", "-jar", "app.jar"]