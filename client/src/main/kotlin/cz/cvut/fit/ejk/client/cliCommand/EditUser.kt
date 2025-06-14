package cz.cvut.fit.ejk.client.cliCommand

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import cz.cvut.fit.ejk.shared.dto.CreateUserDto
import cz.cvut.fit.ejk.shared.dto.GetUserDto
import io.ktor.client.call.body
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.runBlocking

class EditUser: CliktCommand(name = "edit-user") {
    val id by option("--id").int().required()
    val username by option("--username").required()
    val email by option("--email").required()

    override fun run() {
        runBlocking {
            val request = CreateUserDto(username, email)

            val response = restClient.put ("$baseUrl/user/$id") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            if (!response.status.isSuccess()) {
                echo("Error editing the user")
                return@runBlocking
            }
            val body: GetUserDto = response.body()
            echo(body.toString())
        }
    }
}
