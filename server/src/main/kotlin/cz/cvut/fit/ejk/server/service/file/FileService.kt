package cz.cvut.fit.ejk.server.service.file

import java.io.InputStream

interface FileService {
    fun getFile(filename: String): InputStream
    fun saveFile(filename: String, data: InputStream, size: Long, contentType: String)
    fun deleteFile(filename: String)
}