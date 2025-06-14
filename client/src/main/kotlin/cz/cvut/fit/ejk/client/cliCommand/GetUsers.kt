package cz.cvut.fit.ejk.client.cliCommand

import com.github.ajalt.clikt.core.CliktCommand
import cz.cvut.fit.ejk.shared.dto.GetUserDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.runBlocking

class GetUsers: CliktCommand(name = "get-users") {
    override fun run() {
        runBlocking {
            val response = restClient.get("$baseUrl/user")
            if (!response.status.isSuccess()) {
                echo("Error fetching the users")
                return@runBlocking
            }
            val body: List<GetUserDto> = response.body()
            for (user in body) {
                echo(user.toString())
            }
        }
    }
}
