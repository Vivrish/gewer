package cz.cvut.fit.ejk.server


import cz.cvut.fit.ejk.server.domain.DatabaseFactory
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    Thread {
        GrpcServer.start()
    }.start()

    configureMonitoring()
    configureSerialization()
    configureFrameworks()
    configureRouting()
    DatabaseFactory.init()
}
