package cz.cvut.fit.ejk.domain


import cz.cvut.fit.ejk.domain.table.FilesMetaData
import cz.cvut.fit.ejk.domain.table.Users
import cz.cvut.fit.ejk.domain.table.UsersFilesMetadata
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

object DatabaseFactory {
    private val logger = LoggerFactory.getLogger(this::class.java)
    fun init() {
        logger.info("Connecting to database...")
//        Database.connect(
//            url = System.getenv("DB_URL"),
//            user = System.getenv("DB_USER"),
//            password = System.getenv("DB_PASSWORD"),
//        )
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/db",
            user = "postgres",
            password = "secretpassword"
        )
        logger.info("Database connection established.")
        transaction { SchemaUtils.create(Users, FilesMetaData, UsersFilesMetadata) }
        logger.info("Database tables created.")
    }
}