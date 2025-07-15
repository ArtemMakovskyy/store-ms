@echo on
echo ===============================
echo 🚀 Starting Kubernetes cluster KIND
echo ===============================

kind create cluster --name microservices --config kind-config.yaml

echo ===============================
echo ✅ Кластер создан (если нет ошибок)
echo Нажмите любую клавишу для выхода...
pause
