package cz.cvut.fit.ejk.client.cliCommand

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import cz.cvut.fit.ejk.shared.dto.GetFileDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.runBlocking

class GetFiles: CliktCommand(name = "get-files") {
    private val userId by option("--user-id").int().required()

    override fun run() {
        runBlocking {
            val response = restClient.get("$baseUrl/user/$userId/file") {}
            if (!response.status.isSuccess()) {
                echo("Error fetching the files")
                return@runBlocking
            }
            val body: List<GetFileDto> = response.body()
            for (file in body) {
                echo(file.toString())
            }
        }
    }
}
