@echo on

echo Start push artemmakivskyy/notification-service:latest
docker push artemmakivskyy/notification-service:latest

echo Start docker push artemmakivskyy/inventory-service:latest
docker push artemmakivskyy/inventory-service:latest

echo Start docker push artemmakivskyy/order-service:latest
docker push artemmakivskyy/order-service:latest

echo Start docker push artemmakivskyy/product-service:latest
docker push artemmakivskyy/product-service:latest

echo Start docker push artemmakivskyy/discovery-service:latest
docker push artemmakivskyy/discovery-service:latest

echo Start docker push artemmakivskyy/api-gateway-mvc:latest
docker push artemmakivskyy/api-gateway-mvc:latest

echo 🎉 Все образы успешно отправлены!
pause
