package cz.cvut.fit.ejk.client.cliCommand

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import cz.cvut.fit.ejk.shared.dto.CreateUserDto
import cz.cvut.fit.ejk.shared.dto.GetUserDto
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking

class CreateUser: CliktCommand(name = "create-user") {
    private val name by option("--name").required()
    private val email by option("--email").required()
    override fun run() {
        runBlocking {
            val dto = CreateUserDto(name, email)
            val response = restClient.post("$baseUrl/user") {
                contentType(ContentType.Application.Json)
                setBody(dto)
            }
            if (response.status != HttpStatusCode.OK) {
                echo("Error creating a user.")
            }
            val body: GetUserDto = response.body()
            echo("User created: $body")

        }
    }
}
