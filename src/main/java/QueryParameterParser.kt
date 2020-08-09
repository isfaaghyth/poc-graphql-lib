object QueryParameterParser {

    private const val pattern = "\\\$[A-Za-z]+(\\\\.[A-Za-z]+)*: (?:String|Int|Boolean)"
    private val regex = Regex(pattern)

    fun parameters(query: String): List<QueryParams> {
        return regex.findAll(query)
            .map {
                val result = separateParams(it.value)
                QueryParams(result.first, result.second)
            }.toList()
    }

    fun separateParams(param: String): Pair<String, String> {
        val result = param.split(":")
        return Pair(result[0].drop(1).trim(), result[1].trim())
    }

}