package cz.cvut.fit.ejk.server.service.file

import java.io.InputStream

interface FileService {
    fun getFile(filename: String, owner: String): InputStream
    fun saveFile(filename: String, data: InputStream, size: Long, contentType: String, owner: String)
    fun deleteFile(filename: String, owner: String)
}