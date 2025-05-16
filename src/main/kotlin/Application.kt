package cz.cvut.fit.ejk

import cz.cvut.fit.ejk.di.appModule
import cz.cvut.fit.ejk.domain.DatabaseFactory
import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.ktor.plugin.Koin

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
