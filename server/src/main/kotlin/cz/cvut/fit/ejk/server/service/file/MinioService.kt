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
    private val bucketName = System.getenv("MINIO_BUCKET")
    private val minioHost = System.getenv("MINIO_HOST")
    private val client = MinioClient.builder()
        .endpoint("http://$minioHost:9000")
        .credentials(System.getenv("MINIO_ROOT_USER"), System.getenv("MINIO_ROOT_PASSWORD"))
        .build()
    private val logger = LoggerFactory.getLogger(this::class.java)

    init {
        logger.info("MinioService initiated:{} {} {} {}", bucketName, minioHost, System.getenv("MINIO_ROOT_USER"), System.getenv("MINIO_ROOT_PASSWORD"))
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build())
        }
    }
    override fun getFile(filename: String): InputStream {
        return client.getObject(
            GetObjectArgs
                .builder()
                .bucket(bucketName)
                .`object`(filename)
                .build()
        )
    }
    override fun saveFile(filename: String, data: InputStream, size: Long, contentType: String) {
        logger.info("Saving file $filename $size, contentType $contentType")
        client.putObject(
            PutObjectArgs.builder()
                .bucket(bucketName)
                .`object`(filename)
                .stream(data, -1L, PutObjectArgs.MIN_MULTIPART_SIZE.toLong())
                .build()
        )
        logger.info("File is saved in minio")
    }

    override fun deleteFile(filename: String) {
        client.removeObject(
            RemoveObjectArgs
                .builder()
                .bucket(bucketName)
                .`object`(filename)
                .build()
        )
    }

}