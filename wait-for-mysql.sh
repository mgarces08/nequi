#!/bin/sh

echo "Esperando a que MySQL esté listo..."

MAX_TRIES=10
TRIES=0

until nc -z mysql 3306; do
  TRIES=$((TRIES+1))
  if [ "$TRIES" -ge "$MAX_TRIES" ]; then
    echo "MySQL no está disponible después de $MAX_TRIES intentos. Abortando."
    exit 1
  fi
  sleep 2
done

echo "MySQL está listo. Iniciando la aplicación..."
exec java -jar nequi.jar
