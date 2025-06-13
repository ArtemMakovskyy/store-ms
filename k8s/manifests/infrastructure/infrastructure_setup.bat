@echo on

echo Deploying Zookeeper...
kubectl apply -f zookeeper.yml

echo Deploying Kafka...
kubectl apply -f kafka.yml

echo Deploying Kafka + MySQL Connector...
kubectl apply -f kafka-mysql.yml

echo Deploying Kafka UI...
kubectl apply -f kafka-ui.yml

echo Deploying Schema Registry...
kubectl apply -f schema-registry.yml

echo Deploying Prometheus...
kubectl apply -f prometheus.yml

echo Deploying Grafana...
kubectl apply -f grafana.yml

echo Deploying Loki...
kubectl apply -f loki.yml

echo Deploying Tempo...
kubectl apply -f tempo.yml

echo All components deployed.
pause
