# kafka-spring

Как добавить поднятый kafka клатер в kafka-ui. Нажать на кнопку configure new cluster где нужно заполнить следующие поля:

1

    Cluster name - Можно указать любое имя например "Kafka Cluster"

2

    Bootstrap Servers - Сюда нужно вписать значение параметра "KAFKA_ADVERTISED_LISTENERS" в нашем случае PLAINTEXT://kafka:29092


 Соотвественно если, поднято несколько реплик кафки, нужно их всех вписать. Apache рекомендуют иметь 3 ноды с kafka на проекте.

3

    Metrics
    metrics type -> JMX
    port -> 9997


----------------------------------------------------------------------------------------------------    
Kafka запущена можно проверить её работу консольными клиентами.
Команда добавляет консольного продюсера (Kafka CLI producer), после чего через Enter добавляются сообщения (для выхода Ctrl + C)

    /bin/kafka-console-producer --topic demo-topic --bootstrap-server kafka:9092
    
Команда добавляет консольного консьюмера (Kafka CLI consumer) ничего делать ненуждо просто ждем и все сообщения приходят. (для выхода Ctrl + C)

    /bin/kafka-console-consumer --topic demo-topic --from-beginning --bootstrap-server kafka:9092


----------------------------------------------------------------------------------------------------    

