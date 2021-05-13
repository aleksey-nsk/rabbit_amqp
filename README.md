# Info:
1. Пример работы с брокером сообщений **RabbitMQ** на **Spring**
2. Используется библиотека [Spring AMQP](https://spring.io/projects/spring-amqp)
3. Пример взят из [статьи на Хабре](https://habr.com/ru/post/262069/)

# Что было сделано:
- Установлен **RabbitMQ Server** по [инструкции](https://coderun.ru/blog/kak-ustanovit-rabbitmq-server-v-ubuntu-18-04-i-16-04-lts/)
- После того, как пользователь переходит по определённой ссылке (*http://localhost:8080/emit*), в RabbitMQ посылается сообщение
- Листенер просто выводит сообщение в лог
- Отслеживать процессы в RabbitMQ можно через специальную **web консоль**:
![](https://github.com/aleksey-nsk/rabbit_amqp/blob/master/screenshots/01_rabbit_web_console.png)
