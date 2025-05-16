package cz.cvut.fit.ejk.routes

import cz.cvut.fit.ejk.dto.CreateFileDto
import cz.cvut.fit.ejk.dto.GetFileDto
import cz.cvut.fit.ejk.service.FileService
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route


import io.ktor.server.resources.*


@Resource("/file")
class FileLocation{
    @Resource("/{id}")
    data class FileDetail(val parent: FileLocation, val id: Int)
}

fun Route.fileRoutes() {
    val fileService = FileService()

    get<FileLocation> {
        call.respond<List<GetFileDto>>(fileService.getAll())
    }
    post<FileLocation> {
        val file = call.receive<CreateFileDto>()
        call.respond<GetFileDto>(fileService.create(file))
    }
    get<FileLocation.FileDetail> { location ->
        val id = location.id
        call.respond<GetFileDto>(
            fileService.getById(id) ?: return@get call.respond(
                HttpStatusCode.NotFound,
                "File with id $id not found!"
            )
        )
    }
    delete<FileLocation.FileDetail> { location ->
        val id = location.id
        fileService.delete(id)
        call.respond(HttpStatusCode.OK)
    }
    put<FileLocation.FileDetail> { location ->
        val id = location.id
        val file = call.receive<CreateFileDto>()
        call.respond<GetFileDto>(
            fileService.update(id, file) ?: return@put call.respond(
                HttpStatusCode.NotFound,
                "File with id $id not found!"
            )
        )
    }
}