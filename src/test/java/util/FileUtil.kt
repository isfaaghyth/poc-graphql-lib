package util

import com.google.gson.Gson
import java.io.File
import java.lang.reflect.Type

object FileUtil {

    fun readFile(fileName: String): String {
        val fileContent = StringBuilder()
        val filePath = javaClass.getResource(fileName)?.path
        File(filePath).forEachLine { fileContent.append(it) }
        return fileContent.toString()
    }

    fun <T> parseJson(fileName: String, typeOfT: Type): T {
        val stringFile = readFile(fileName)
        return Gson().fromJson(stringFile, typeOfT)
    }

}