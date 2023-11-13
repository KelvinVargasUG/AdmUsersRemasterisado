#!/bin/bash

echo "Starting the database wait script..."

for _ in {1..150}; do
  echo "esperando a base de dados..."
  sleep 1
done

exec java -jar /AdmUsers.jar
