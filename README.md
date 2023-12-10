

Создаем отдельную подсеть для работы Kafka:

    docker network create kafkanet

Создаем контейнер с Zookeeper

    docker run -d --network=kafkanet --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 confluentinc/cp-zookeeper

Создаем контейнер с Kafka

    docker run -d --network=kafkanet --name=kafka -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -p 9092:9092 confluentinc/cp-kafka


Заходим на брокер:

    docker exec -it kafka bash



Создаем топик:

    /bin/kafka-topics --create --topic demo-topic --bootstrap-server kafka:9092

Посмотреть количество топиков:

    /bin/kafka-topics --list --bootstrap-server localhost:9092

Посмотреть описание топика в том числе количество партиций:

    /bin/kafka-topics --describe --topic demo-topic --bootstrap-server kafka:9092

----------------------------------------------------------------------------------------------------    
Kafka запущена можно проверить её работу консольными клиентами.
Команда добавляет консольного продюсера (Kafka CLI producer), после чего через Enter добавляются сообщения (для выхода Ctrl + C)

    /bin/kafka-console-producer --topic demo-topic --bootstrap-server kafka:9092
    
Команда добавляет консольного консьюмера (Kafka CLI consumer) ничего делать ненуждо просто ждем и все сообщения приходят. (для выхода Ctrl + C)

    /bin/kafka-console-consumer --topic demo-topic --from-beginning --bootstrap-server kafka:9092


----------------------------------------------------------------------------------------------------    

Базовую работоспособность проверили, идем дальше. Запускаем консьюмера и продюсера на Java. Поиграем с количеством partitions и с количеством consumers в consumer group:

Создать 3 партиции в топике(demo-topic):

    /bin/kafka-topics --bootstrap-server localhost:9092 --alter --topic demo-topic --partitions 3

Посмотреть описание топика в том числе количество партиций:

    /bin/kafka-topics --describe --topic demo-topic --bootstrap-server kafka:9092


Это надо запустить в 4х разных консолях одновременно чтобы увидеть что для одного consumer не хватило partition:
(команда добавляет консольного консьюмера, каждый раз запускается в отдельной консоли)

    docker exec -it kafka /bin/kafka-console-consumer --topic demo-topic --group demo-group --bootstrap-server kafka:9092
