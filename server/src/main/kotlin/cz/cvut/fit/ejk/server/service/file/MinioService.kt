package cz.cvut.fit.ejk.server.service.file

import io.minio.BucketExistsArgs
import io.minio.GetObjectArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import org.koin.core.component.KoinComponent
import org.slf4j.LoggerFactory
import java.io.InputStream

class MinioService: FileService, KoinComponent {
    private val minioHost = System.getenv("MINIO_HOST")
    private val client = MinioClient.builder()
        .endpoint("http://$minioHost:9000")
        .credentials(System.getenv("MINIO_ROOT_USER"), System.getenv("MINIO_ROOT_PASSWORD"))
        .build()
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun getFile(filename: String, owner: String): InputStream {
        return client.getObject(
            GetObjectArgs
                .builder()
                .bucket(owner)
                .`object`(filename)
                .build()
        )
    }
    override fun saveFile(filename: String, data: InputStream, size: Long, contentType: String, owner: String) {
        logger.info("Saving file $filename $size, contentType $contentType")
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(owner).build())) {
            client.makeBucket(MakeBucketArgs.builder().bucket(owner).build())
        }
        client.putObject(
            PutObjectArgs.builder()
                .bucket(owner)
                .`object`(filename)
                .stream(data, -1L, PutObjectArgs.MIN_MULTIPART_SIZE.toLong())
                .build()
        )
        logger.info("File is saved in minio")
    }

    override fun deleteFile(filename: String, owner: String) {
        client.removeObject(
            RemoveObjectArgs
                .builder()
                .bucket(owner)
                .`object`(filename)
                .build()
        )
    }

}