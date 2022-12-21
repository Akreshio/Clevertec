# Clevertec test task
### 1. Используемые технологии
SPRING FRAMEWORK, LIQUIBASE, POSTGRESQL 
### 2. Запуск приложения
При первом запуске приложение посредством liquibase создаёт и заполняет таблицы данных
доступ к приложению через url: localhost:8080/check?item=2-2,22-7,24-1,12-8,18-9,17-1,card-1  где item=2-2,…,card-1 набор принимаемых параметров. Для товара {id}-{count} для карты card-{id}.
Аналогично и для формирования файла url: localhost:8080/check/file?item=2-2
