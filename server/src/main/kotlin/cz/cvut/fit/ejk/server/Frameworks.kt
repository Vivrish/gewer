package cz.cvut.fit.ejk.server

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.Resources
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import cz.cvut.fit.ejk.server.di.appModule

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    install(Resources)
}
