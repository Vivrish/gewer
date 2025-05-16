package cz.cvut.fit.ejk

import cz.cvut.fit.ejk.routes.fileRoutes
import cz.cvut.fit.ejk.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Default page")
        }
        userRoutes()
        fileRoutes()
    }
}
