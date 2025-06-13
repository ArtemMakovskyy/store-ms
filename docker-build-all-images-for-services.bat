@echo on

echo Start creating notification-service:latest
docker build ./notification-service --tag=notification-service:latest
echo Start creating artemmakivskyy/notification-service:latest
docker tag notification-service:latest artemmakivskyy/notification-service:latest

echo Start creating inventory-service:latest
docker build ./inventory-service --tag=inventory-service:latest
echo Start creating artemmakivskyy/inventory-service:latest
docker tag inventory-service:latest artemmakivskyy/inventory-service:latest

echo Start creating order-service:latest
docker build ./order-service --tag=order-service:latest
echo Start creating artemmakivskyy/order-service:latest
docker tag order-service:latest artemmakivskyy/order-service:latest

echo Start creating product-service:latest
docker build ./product-service --tag=product-service:latest
echo Start creating artemmakivskyy/product-service:latest
docker tag product-service:latest artemmakivskyy/product-service:latest

echo Start creating discovery-service:latest
docker build ./discovery-service --tag=discovery-service:latest
echo Start creating artemmakivskyy/discovery-service:latest
docker tag discovery-service:latest artemmakivskyy/discovery-service:latest

echo Start creating api-gateway:latest
docker build ./api-gateway --tag=api-gateway:latest
echo Start creating artemmakivskyy/api-gateway:latest
docker tag api-gateway:latest artemmakivskyy/api-gateway:latest

echo Start creating api-gateway-mvc:latest
docker build ./api-gateway-mvc --tag=api-gateway-mvc:latest
echo Start creating artemmakivskyy/api-gateway-mvc:latest
docker tag api-gateway-mvc:latest artemmakivskyy/api-gateway-mvc:latest

echo 🎉 Все образы успешно собраны и промаркированы!
pause
