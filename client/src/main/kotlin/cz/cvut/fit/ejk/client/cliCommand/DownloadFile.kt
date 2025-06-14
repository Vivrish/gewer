package cz.cvut.fit.ejk.client.cliCommand
import FileServiceOuterClass
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import cz.cvut.fit.ejk.shared.dto.GetFileDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking
import java.io.File

class DownloadFile: CliktCommand(name = "download"){
    private val fileId by option("--id").int()


    override fun run() {
        runBlocking {
            val getFileResponse: GetFileDto = restClient.get("$baseUrl/file/$fileId"){
                contentType(ContentType.Application.Json)
            }.body()

            val file = File(getFileResponse.filename)
            val request = FileServiceOuterClass.DownloadRequest.newBuilder().setFileId(getFileResponse.id).build()
            val stub = FileServiceGrpcKt.FileServiceCoroutineStub(channel)

            file.outputStream().use { outputStream ->
                val responseFlow = stub.downloadFile(request)
                responseFlow.collect { response ->
                    val bytes = response.toByteArray()
                    outputStream.write(bytes)
                }
            }

            channel.shutdown()
        }
    }
}
