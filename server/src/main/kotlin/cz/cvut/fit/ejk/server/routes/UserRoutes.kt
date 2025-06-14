package cz.cvut.fit.ejk.server.routes

import cz.cvut.fit.ejk.server.service.crud.UserService
import cz.cvut.fit.ejk.shared.dto.CreateUserDto
import cz.cvut.fit.ejk.shared.dto.GetFileDto
import cz.cvut.fit.ejk.shared.dto.GetUserDto
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

import io.ktor.server.resources.*


@Resource("/user")
class UsersLocation {
    @Resource("/{id}")
    data class UserDetail(val parent: UsersLocation, val id: Int) {
        @Resource("/file")
        data class UserFiles(val parent: UserDetail) {
            @Resource("/{fileId}")
            data class FileDetail(val parent: UserFiles, val fileId: Int) {
            }
        }
    }
}

fun Route.userRoutes(userService: UserService) {
    get<UsersLocation> {
        call.respond<List<GetUserDto>>(userService.getAll())
    }
    post<UsersLocation> {
        val userToCreate = call.receive<CreateUserDto>()
        call.respond<GetUserDto>(userService.create(userToCreate))
    }
    get<UsersLocation.UserDetail> { location ->
        val id = location.id
        call.respond<GetUserDto>(
            userService.getById(id) ?: return@get call.respond(
                HttpStatusCode.NotFound,
                "User with id $id not found!"
            )
        )
    }
    put<UsersLocation.UserDetail> { location ->
        val id = location.id
        val userToUpdate = call.receive<CreateUserDto>()
        call.respond<GetUserDto>(
            userService.update(id, userToUpdate) ?: return@put call.respond(
                HttpStatusCode.NotFound,
                "User with id $id not found!"
            )
        )
    }
    delete<UsersLocation.UserDetail> { location ->
        val id = location.id
        userService.delete(id)
        call.respond(HttpStatusCode.OK)
    }

    get<UsersLocation.UserDetail.UserFiles> { location ->
        val id = location.parent.id
        call.respond<List<GetFileDto>>(userService.getFiles(id))
    }

    post<UsersLocation.UserDetail.UserFiles.FileDetail> { location ->
        val id = location.parent.parent.id
        val fileId = location.fileId
        call.respond<GetUserDto>(
            userService.addFile(id, fileId) ?: return@post call.respond(
                HttpStatusCode.NotFound,
                "User with id $id not found!"
            )
        )
    }
    delete<UsersLocation.UserDetail.UserFiles.FileDetail> { location ->
        val id = location.parent.parent.id
        val fileId = location.fileId
        call.respond<GetUserDto>(
            userService.removeFile(id, fileId) ?: return@delete call.respond(
                HttpStatusCode.NotFound,
                "User with id $id not found!"
            )
        )
    }

}