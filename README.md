# cathbybk-api

## 專案工具

1. Java jdk 17
2. Spring Boot 3
3. Postgresql
4. Docker

## DB Schema

db 資料夾下的 sqlSchema.sql 為 建立 Database 以及 Table 語法。

## 啟動方式

下載此專案至本地或是
```shell
  git clone https://github.com/YuyingCYY/cathbybk-api.git
```
完成後在此專案資料夾下打開終端機

### Docker 直接執行

```shell
  docker-compose up --build
```

### Maven 直接執行

請先確保已經有 Postgresql，並且按照語法建立資料庫及資料表

```shell
  mvn spring-boot:run
```

### JAR 檔案運行

請先確保已經有 Postgresql，並且按照語法建立資料庫及資料表

執行 Maven 構建
```shell
  mvn clean package
```

JAR 檔案運行應用：
```shell
  java -jar target/cathbybk-api-0.0.1-SNAPSHOT.jar
```

## 執行 API

api 基本 url 為:http://localhost:8080/api/fund

可參考專案底下：src -> resource -> test.http 檔案。

可使用開發軟體直接執行此 http 例如：VS code(下載相關套件)、Intellij 等。

也可使用 postman 軟體來呼叫 api。
