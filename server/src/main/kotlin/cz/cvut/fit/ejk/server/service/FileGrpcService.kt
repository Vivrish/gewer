package cz.cvut.fit.ejk.server.service

import FileServiceGrpcKt
import FileServiceOuterClass
import com.google.protobuf.ByteString
import cz.cvut.fit.ejk.server.common.GrpcInputStream
import cz.cvut.fit.ejk.server.service.crud.FileMetadataService
import cz.cvut.fit.ejk.server.service.file.FileService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class FileGrpcService: FileServiceGrpcKt.FileServiceCoroutineImplBase(), KoinComponent {
    val fileService: FileService by inject()
    val fileMetadataService: FileMetadataService by inject()
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun uploadFile(requests: Flow<FileServiceOuterClass.UploadRequest>): FileServiceOuterClass.UploadResponse {
        logger.info("Accepted upload file request: {}", requests)
        val first = requests.first()
        val fileId = first.fileId
        val chunkFlow = flow {
            emit(first.chunk)
            emitAll(requests.drop(1).map { it.chunk })
        }
        val fileMetadata = fileMetadataService.getById(fileId)

        if (fileMetadata == null) {
            logger.error("filemetadata not found: {}", fileId)
            return FileServiceOuterClass.UploadResponse.newBuilder().setSuccess(false).build()
        }

        try {
        val inputStream = GrpcInputStream(chunkFlow)
            fileService.saveFile(
                fileMetadata.filename,
                inputStream,
                fileMetadata.size,
                fileMetadata.fileType,
                fileMetadata.users.first()
            )
        }
        catch (e: Exception){
            logger.error("Error while saving file: {} {} {}", e.message, e.cause, e.stackTrace)
            fileMetadataService.delete(fileMetadata.id)
            return FileServiceOuterClass.UploadResponse.newBuilder().setSuccess(false).build()
        }
        logger.info("Successfully saved file")
        return FileServiceOuterClass.UploadResponse.newBuilder().setSuccess(true).build()
    }

    override fun downloadFile(request: FileServiceOuterClass.DownloadRequest): Flow<FileServiceOuterClass.DownloadResponse> = flow {
        logger.info("Accepted download file request: {}", request)
        val fileMetadata = fileMetadataService.getById(request.fileId) ?: return@flow
        val inputStream = fileService.getFile(fileMetadata.filename, fileMetadata.users.first())

        val buffer = ByteArray(64 * 1024)

        try {
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                emit(FileServiceOuterClass.DownloadResponse.newBuilder().setChunk(ByteString.copyFrom(buffer, 0, bytesRead)).build())
            }
        } finally {
            inputStream.close()
        }

    }
}