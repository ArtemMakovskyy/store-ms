@echo on
echo Applying mongo-db...
kubectl apply -f mongodb.yaml

echo Applying MySQL-order-db...
kubectl apply -f mysql-order.yaml

echo Applying postgres-inventory-db...
kubectl apply -f postgres-inventory.yaml

echo Applying postgres-product-db...
kubectl apply -f postgres-product.yaml

echo Done.
pause
