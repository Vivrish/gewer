# gewer

REST/gRPC client-server application written in Kotlin using a Ktor framework. Uses Minio (S3 compatible storage) and postgres database.

Provides functionality to download/upload files via gRPC and manage users and file metadata with REST API

# Build

Server: 

1) install docker runtime

2) docker-compose up

Client:

1) install gradle and java
2) gradle -p client shadowJar
3) java -jar ./client/build/libs/client-all.jar