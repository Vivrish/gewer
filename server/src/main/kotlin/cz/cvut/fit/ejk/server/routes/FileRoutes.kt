package cz.cvut.fit.ejk.server.routes

import cz.cvut.fit.ejk.server.service.crud.FileMetadataService
import cz.cvut.fit.ejk.server.service.file.FileService
import cz.cvut.fit.ejk.shared.dto.CreateFileDto
import cz.cvut.fit.ejk.shared.dto.GetFileDto
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route


import io.ktor.server.resources.*
import org.koin.ktor.ext.inject


@Resource("/file")
class FileLocation{
    @Resource("/{id}")
    data class FileDetail(val parent: FileLocation, val id: Int)
}

fun Route.fileRoutes(fileMetadataService: FileMetadataService, fileService: FileService) {
    get<FileLocation> {
        call.respond<List<GetFileDto>>(fileMetadataService.getAll())
    }
    post<FileLocation> {
        val file = call.receive<CreateFileDto>()
        call.respond<GetFileDto>(fileMetadataService.create(file))
    }
    get<FileLocation.FileDetail> { location ->
        val id = location.id
        call.respond<GetFileDto>(
            fileMetadataService.getById(id) ?: return@get call.respond(
                HttpStatusCode.NotFound,
                "File with id $id not found!"
            )
        )
    }
    delete<FileLocation.FileDetail> { location ->
        val id = location.id
        val fileToDelete = fileMetadataService.getById(id)
        if (fileToDelete == null) {
            return@delete call.respond(HttpStatusCode.NotFound, "File with id $id not found!")
        }
        fileService.deleteFile(fileToDelete.path)
        fileMetadataService.delete(id)
        call.respond(HttpStatusCode.OK)
    }
    put<FileLocation.FileDetail> { location ->
        val id = location.id
        val file = call.receive<CreateFileDto>()
        call.respond<GetFileDto>(
            fileMetadataService.update(id, file) ?: return@put call.respond(
                HttpStatusCode.NotFound,
                "File with id $id not found!"
            )
        )
    }
}