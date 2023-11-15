#!/bin/bash

echo "Iniciando el script de espera para la base de datos... Este tiene un tiempo de espera de 3 minutos."

seconds=0

for ((i = 1; i <= 130; i++)); do
  echo "Esperando que la base de datos termine de cargar... Han pasado $seconds segundos."
  sleep 1
  ((seconds++))
done
echo "INICIANDO APP..."

exec java -jar /AdmUsers.jar