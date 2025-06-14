package cz.cvut.fit.ejk.server

import cz.cvut.fit.ejk.server.routes.fileRoutes
import cz.cvut.fit.ejk.server.routes.userRoutes
import cz.cvut.fit.ejk.server.service.crud.FileMetadataService
import cz.cvut.fit.ejk.server.service.crud.UserService
import cz.cvut.fit.ejk.server.service.file.FileService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Application.configureRouting() {
    val userService: UserService by inject()
    val fileService: FileService by inject()
    val fileMetadataService: FileMetadataService by inject()

    routing {
        userRoutes(userService)
        fileRoutes(fileMetadataService, fileService)
    }
}
