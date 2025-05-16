package cz.cvut.fit.ejk.di

import cz.cvut.fit.ejk.service.FileService
import cz.cvut.fit.ejk.service.UserService
import org.koin.dsl.module

val appModule = module {
    single<UserService> { UserService( ) }
    single<FileService> { FileService( ) }
}