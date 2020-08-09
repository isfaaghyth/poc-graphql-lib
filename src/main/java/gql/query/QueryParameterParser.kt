package gql.query

import gql.entity.QueryParams

object QueryParameterParser {

    private const val pattern = "\\\$[A-Za-z|0-9]+(\\.[A-Za-z]+)*: (?:String|Int|Boolean)"
    private val regex = Regex(pattern)

    @Deprecated("please use parameters")
    fun oldParameters(query: String): List<QueryParams> {
        return regex.findAll(query)
            .map {
                val result = separateParams(it.value)
                QueryParams(result.first, result.second)
            }.toList()
    }

    fun parameters(query: String): Map<String, Any> {
        val temp = mutableMapOf<String, Any>()
        regex.findAll(query).forEach {
            val result = separateParams(it.value)
            temp[result.first] = result.second
        }
        return temp
    }

    fun separateParams(param: String): Pair<String, String> {
        val result = param.split(":")
        val variable = result[0].drop(1).trim()
        val dataType = "java.lang.${result[1].trim().replaceInt()}"
        return Pair(variable, dataType)
    }

    private fun String.replaceInt(): String {
        if (this == "Int") return "Integer"
        return this
    }

}