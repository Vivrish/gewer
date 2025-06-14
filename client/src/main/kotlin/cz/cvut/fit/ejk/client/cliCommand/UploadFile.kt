package cz.cvut.fit.ejk.client.cliCommand


import FileServiceGrpcKt
import FileServiceOuterClass
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import com.google.protobuf.ByteString
import cz.cvut.fit.ejk.shared.dto.CreateFileDto
import cz.cvut.fit.ejk.shared.dto.GetFileDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.io.File


class UploadFile: CliktCommand(name = "upload") {
    private val file by option("--file").file(mustExist = true, mustBeReadable = true).required()
    private val id by option("--id").int().required()

    fun fileToChunkedFlow(file: File, fileId: Int): Flow<FileServiceOuterClass.UploadRequest> = flow {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        file.inputStream().use { input ->
            var bytesRead = 0
            while(input.read(buffer).also { bytesRead = it } != -1) {
                emit(
                    FileServiceOuterClass
                        .UploadRequest
                        .newBuilder()
                        .setFileId(fileId)
                        .setChunk(ByteString.copyFrom(buffer, 0, bytesRead))
                        .build())
            }
        }
    }


    override fun run() {
        runBlocking {
            val body = CreateFileDto(file.name, file.path, file.extension, file.length())

            val createFileResponse: GetFileDto = restClient.post("$baseUrl/file") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }.body()
            val fileId = createFileResponse.id

            val getUserResponse = restClient.get("$baseUrl/user/$id")

            if (!getUserResponse.status.isSuccess()) {
                echo("User not found")
                return@runBlocking
                }
            if (!restClient.post("$baseUrl/user/$id/file/$fileId").status.isSuccess()) {
                echo("Could not add file $fileId to user $id")
                }

            val stub = FileServiceGrpcKt.FileServiceCoroutineStub(channel)
            stub.uploadFile(fileToChunkedFlow(file, fileId))

            channel.shutdown()
        }
    }

}
