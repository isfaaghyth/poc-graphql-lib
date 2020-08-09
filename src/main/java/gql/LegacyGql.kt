package gql

import gql.entity.QueryParams
import gql.query.QueryParameterParser
import gql.util.LegacyGqlException

@Deprecated("Please use Gql.kt")
open class LegacyGql {

    private val params = mutableListOf<QueryParams>()
    private lateinit var query: String

    fun queries(rawQuery: String) = apply {
        query = rawQuery
        collectParameters()
    }

    fun parameters(gqlParams: Array<*>) = apply {
        if (gqlParams.size != params.size) return LegacyGqlException(ArrayIndexOutOfBoundsException)
        gqlParams.toList().forEachIndexed { index, any ->
            any?.let {
                if (it.javaClass != Class.forName(params[index].type)) {
                    return LegacyGqlException(IllegalArgumentException)
                }
            }
        }
    }

    private fun collectParameters() = apply {
        params.clear()
        params.addAll(QueryParameterParser.oldParameters(query))
    }

    companion object {
        private const val ArrayIndexOutOfBoundsException = "gql: ArrayIndexOutOfBoundsException"
        private const val IllegalArgumentException = "gql: IllegalArgumentException"
    }

}