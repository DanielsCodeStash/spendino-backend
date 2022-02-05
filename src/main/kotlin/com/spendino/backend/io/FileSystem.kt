package com.spendino.backend.io

import java.io.File
import kotlin.system.exitProcess

private const val cardFileName = "card.txt"
private const val bankFileName = "bank.txt"

class FileSystem(
    val month: String,
    val basePath: String,
    private val monthDirectoryPath: String = basePath + month,
    private val monthDirectory: File = File(monthDirectoryPath)
) {

    init {
        prepareMonthDirectory()
        copyInputFiles()
    }

    private fun copyInputFiles() {
        listOf(cardFileName, bankFileName)
            .forEach { fileName ->
                val fromFile = fetchFile(basePath + fileName)
                val toFile = File(monthDirectoryPath + "\\" + fileName)
                fromFile.copyTo(toFile, true)
                println("Copying ${fromFile.path} to month directory")
            }
    }

    fun cardFile(): File = File(basePath + cardFileName)
    fun bankFile(): File = File(basePath + bankFileName)

    fun getNewFilePath(
        filename: String,
        monthPrefix: Boolean = true,
        removeDashes: Boolean = true)
    : String {
        var finalFilename = if(monthPrefix) month + filename else filename
        finalFilename = if(removeDashes) finalFilename.replace("-", "") else finalFilename
        return monthDirectoryPath + "\\" + finalFilename
    }

    private fun fetchFile(path: String): File {
        val file = File(path)

        if (!file.exists()) {
            System.err.println("Failed to find file that's required: $path")
            exitProcess(1)
        }
        return file
    }

    private fun prepareMonthDirectory() {

        if (monthDirectory.exists()) {
            if (!monthDirectory.isDirectory) {
                System.err.println("$monthDirectoryPath is not a directory?")
                exitProcess(1)
            }
        } else {
            val directoryCreated = monthDirectory.mkdir()
            if (directoryCreated) {
                println("Created storage directory $monthDirectoryPath")
            } else {
                System.err.println("Failed to create month storage directory $monthDirectoryPath")
                exitProcess(1)
            }
        }
    }
}