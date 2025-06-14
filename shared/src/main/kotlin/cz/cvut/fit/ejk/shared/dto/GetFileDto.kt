package cz.cvut.fit.ejk.shared.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetFileDto(val id: Int, val path: String, val filename: String, val size: Long, val fileType: String, val users: List<String>){
    override fun toString(): String {
        return "File: id=$id, path='$path', filename='$filename', size=$size, fileType='$fileType', users=$users"
    }
}
