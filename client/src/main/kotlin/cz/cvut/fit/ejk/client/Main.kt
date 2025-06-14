package cz.cvut.fit.ejk.client

import cz.cvut.fit.ejk.client.cliCommand.CreateUser
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import cz.cvut.fit.ejk.client.cliCommand.DownloadFile
import cz.cvut.fit.ejk.client.cliCommand.UploadFile

class Cli: CliktCommand() {
    override fun run() {

    }
}

fun main(args: Array<String>) {
    Cli().subcommands(CreateUser(), DownloadFile(), UploadFile()).main(args)
}


