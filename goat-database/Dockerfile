FROM postgres AS create_db

WORKDIR /app
EXPOSE 5432

COPY ./init.sql /docker-entrypoint-initdb.d/
COPY ./populate.sql /docker-entrypoint-initdb.d/

FROM create_db AS seed_db

COPY ./populate.development.sql /docker-entrypoint-initdb.d/

FROM create_db AS production_seed

COPY ./populate.production.sql /docker-entrypoint-initdb.d/