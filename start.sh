#!/bin/bash

docker compose -f ./database/docker-compose.yml up -d
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk env && ./mvnw test
sdk env && ./mvnw spring-boot:run