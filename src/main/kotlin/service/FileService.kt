package cz.cvut.fit.ejk.service

import cz.cvut.fit.ejk.domain.model.FileMetadata

class FileService(): CrudService<Int, FileMetadata>(FileMetadata) {

}