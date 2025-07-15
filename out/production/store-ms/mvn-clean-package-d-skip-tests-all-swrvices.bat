@echo off
setlocal enabledelayedexpansion

set services=api-gateway api-gateway-mvc discovery-service inventory-service notification-service order-service product-service
set basePath=D:\Documents\Java\UltimateJetBrains\mateacademy\java\projects\Programming Techie\store-ms

for %%s in (%services%) do (
    echo Building %%s...
    cd %basePath%\%%s
    mvn clean package -DskipTests
)

echo Done!
