# Introducción

Este repositorio contiene dos microservicios: `account-service` y `client-service`, ambos desarrollados con Spring Boot
3, Gradle y Java 21. Los microservicios se comunican de manera asíncrona a través de RabbitMQ y cada uno gestiona su
propia base de datos PostgreSQL, la cual es inicializada automáticamente con Flyway.

## Tecnologías Utilizadas

- **Spring Boot 3**: Framework principal para el desarrollo de ambos microservicios.
- **Gradle**: Gestión de dependencias y compilación.
- **Java 21**: Lenguaje de programación utilizado.
- **RabbitMQ**: Para la comunicación asíncrona entre los microservicios.
- **PostgreSQL**: Base de datos relacional para persistir los datos.
- **Flyway**: Herramienta para la gestión de migraciones de la base de datos.
- **Docker**: Para la contenedorización y despliegue de los servicios.

## Estructura del Proyecto

### account-service

Este microservicio se encarga de manejar las operaciones sobre las cuentas y transacciones (movimientos de la cuenta).

- **Base de datos**: Gestiona las tablas `account` y `transaction`.
- **Servicio REST**: Por defecto se ejecuta en el puerto `8090` y proporciona un conjunto de operaciones CRUD para
  gestionar Cuentas y Transacciones.

### client-service

Este microservicio maneja la información de los clientes.

- **Base de datos**: Gestiona la tabla `client`.
- **Servicio REST**: Por defecto se ejecuta en el puerto `8080` y proporciona un conjunto de operaciones CRUD para
  gestionar Clientes. Además, proporciona un endpoint para generar reportes de transacciones filtrado por cliente en un
  rango de fechas.

## Endpoints REST

El repositorio incluye una colección de Postman `challenge-services.postman_collection.json` con ejemplos para consumir cada uno de los servicios expuestos por
`account-service` y `client-service`.

Se deben configurar las siguientes variables a nivel de colección para establecer la URL y el puerto de cada servicio:

- `client_service_url`
- `account_service_url`

## Ejecutar Servicios

Para poder ejecutar los microservicios se incluye un archivo de docker compose `compose-prod.yml`, que construye las
imágenes docker de los 2 microservicios mediante un `Dockerfile` y levanta las instancias de los servicios necesarios (
Base de datos y RabbitMQ).

### Requisitos

- **Docker** y **Docker Compose** instalados en el sistema.

### Levantar todos los servicios

```sh
docker compose -f compose-prod.yml --env-file .env.example up -d
```

### Eliminar todos los servicios

```sh
docker compose -f compose-prod.yml --env-file .env.example down -v --rmi local
```

## Desarrollo

Se incluye un archivo de docker compose `compose.yml`, que incluye los servicios necesarios para desarrollo (Base de
datos y RabbitMQ).

### Requisitos

- **Docker** y **Docker Compose** instalados en el sistema.
- **JDK Java 21**.

### Levantar todos los servicios

```sh
docker compose up -d
```

### Eliminar todos los servicios

```sh
docker compose down -v
```

## Comandos Útiles

A continuación se presentan algunos comandos útiles para gestionar los servicios del proyecto:

### Ver logs de todos los servicios

```sh
docker compose -f compose-prod.yml --env-file .env.example logs -f
```

### Recrear y levantar los servicios

```sh
docker compose -f compose-prod.yml --env-file .env.example up -d --force-recreate
```

### Reconstruir y levantar los servicios

```sh
docker compose -f compose-prod.yml --env-file .env.example up -d --build
```
