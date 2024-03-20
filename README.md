# formacao-kafka
## Comandos

### Iniciar Zookeeper
```
bin/zookeeper-server-start.sh ../config/zookeeper.properties
```

### Iniciar Kafka
```
bin/kafka-server-start.sh ../config/server.properties
```

### Descrever Tópicos
```
 bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe
```

### Alterar número de Partições de um Tópico

```
 bin/kafka-topics.sh --alter --zookeeper localhost:2181 --topic ECOMMERCE_NEW_ORDER -- partitions 3
```

### Descrever Grupos de Consumo
```
 bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe
```

### Iniciar Consumidor
```
bin/kafka-console-consumer.sh --all-groups --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER --offset earliest --partition 0
```