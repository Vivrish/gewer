package cz.cvut.fit.ejk.client.cliCommand

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import io.ktor.client.request.delete
import io.ktor.http.isSuccess
import kotlinx.coroutines.runBlocking

class DeleteUser: CliktCommand(name = "delete-user") {
    private val userId by option("--id").int().required()

    override fun run() {
        runBlocking {
            val response = restClient.delete("$baseUrl/user/$userId")
            if (!response.status.isSuccess()) {
                echo("Error deleting the user")
                return@runBlocking
            }
            echo("User deleted successfully")
        }
    }
}
