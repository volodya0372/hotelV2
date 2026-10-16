# Запуск приложения

Запустить проект можно с помощью команд
```bash
mvn clean install
mvn spring-boot:run -pl hostel-service-app
```
## Модули
- `hostel-service-api` — Апи слой в котором лежит контроллер и ДТО которые используются для них
- `hostel-service-app` — Слой который разделен на еще на два слоя: 
- app - этот слой отвечает за внешнее соединения с какими-либо системами(к примеру БД или REST),
- core - тут находится бизнес логика и модели. Этот слой про app ничего не знает


## API
Все запросы имеют префикс `/property-view`:
- `GET /property-view/hotels`
- `GET /property-view/hotels/{id}`
- `GET /property-view/search?name=hilton&brand=Hilton&city=minsk&country=Belarus&amenities=Free%20WiFi`
- `POST /property-view/hotels`
- `POST /property-view/hotels/{id}/amenities`
- `GET /property-view/histogram/{brand|city|country|amenities}`

Swagger UI: `http://localhost:8092/swagger-ui.html`
H2 console: `http://localhost:8092/h2-console` (JDBC URL `jdbc:h2:mem:propertyview`, user `sa`, blank password).