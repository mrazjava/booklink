FROM postgres:12.2

ENV POSTGRES_MULTIPLE_DATABASES="booklink_sndbx_local","booklink_sndbx_stg","booklink_sndbx_pre","booklink_sndbx_live"
ENV POSTGRES_USER=bookworm
ENV POSTGRES_PASSWORD=BL

COPY pg-init.sh /docker-entrypoint-initdb.d/