@echo on
echo Applying MySQL Secret...
kubectl apply -f mysql-secret.yaml

echo Applying MySQL ConfigMap...
kubectl apply -f mysql-config.yaml

echo Applying MySQL Deployment...
kubectl apply -f mysql-deployment.yaml

echo Done.
pause
