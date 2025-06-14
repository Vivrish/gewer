package cz.cvut.fit.ejk.server

import cz.cvut.fit.ejk.server.service.FileGrpcService
import io.grpc.ServerBuilder

object GrpcServer {
    fun start() {
        val server = ServerBuilder
            .forPort(50051)
            .addService(FileGrpcService())
            .build()
            .start()
        Runtime.getRuntime().addShutdownHook(Thread {
            server.shutdown()
        })
    }
}