import java.io.File

fun main() {

    val dataPath = System.getProperty("data_folder")

    val fileNames = File(dataPath)
        .walk()
        .map{it.name}
        .filter{it.startsWith("20")}
        .toList()

    println(fileNames)
}

