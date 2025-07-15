@echo on
echo "===Starting Kind Cluster==="

cd kind\
kind create cluster --name microservices --config kind-config.yaml

echo "===Kind Cluster Started==="
echo Press any button for exit ...
pause
