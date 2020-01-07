import com.spendino.backend.data.SpendingEntry
import com.spendino.backend.data.StatementEntry
import com.spendino.backend.io.InputFileType
import com.spendino.backend.io.StatementReader
import com.spendino.backend.logic.EntryCategorizer
import java.io.File

fun main() {

    val files = FileReader().getDataFiles()

    val statements = StatementReader().parseFile(files[0], InputFileType.CARD);

    val categorizer = EntryCategorizer();
    val outSpendingEntries = ArrayList<SpendingEntry>()


    for(statementEntry in statements){

        val spendingEntry = categorizer.categorize(statementEntry) ?: continue;

        val existing = outSpendingEntries.find { it.category == spendingEntry.category && it.subCategory == spendingEntry.subCategory }
        if(existing != null) {
            existing.amount += spendingEntry.amount
        } else {
            outSpendingEntries.add(spendingEntry)
        }
    }

    outSpendingEntries.sortBy { it.category }

    outSpendingEntries.forEach { println(it) }
}

class FileReader {

    fun getDataFiles() : List<String> {

        if(1==1)
            return listOf("C:\\Daniel\\other_projects\\spendino-backend\\backend_data\\card.txt")

        val dataPath = System.getProperty("data_folder")

        return File(dataPath)
                .walk()
                .filter{it.name.startsWith("20")}
                .map{it.absolutePath}
                .toList()
    }

}
