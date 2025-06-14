package cz.cvut.fit.ejk.server

import cz.cvut.fit.ejk.server.di.appModule
import cz.cvut.fit.ejk.server.domain.DatabaseFactory
import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.ktor.plugin.Koin

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
