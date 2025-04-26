package cz.cvut.fit.ejk

import cz.cvut.fit.ejk.domain.DatabaseFactory
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureFrameworks()
    configureRouting()
    DatabaseFactory.init()
}
