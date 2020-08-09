package gql

import gql.entity.QueryParams
import gql.query.QueryParameterParser
import gql.util.GqlException

open class Gql {

    private val params = mutableListOf<QueryParams>()
    private lateinit var query: String

    fun queries(rawQuery: String) = apply {
        query = rawQuery
        collectParameters()
    }

    fun parameters(gqlParams: Array<*>) = apply {
        if (gqlParams.size != params.size) return GqlException(ArrayIndexOutOfBoundsException)
        gqlParams.toList().forEachIndexed { index, any ->
            any?.let {
                if (it.javaClass != Class.forName(params[index].type)) {
                    return GqlException(IllegalArgumentException)
                }
            }
        }
    }

    private fun collectParameters() = apply {
        params.clear()
        params.addAll(QueryParameterParser.parameters(query))
    }

    companion object {
        private const val ArrayIndexOutOfBoundsException = "gql: ArrayIndexOutOfBoundsException"
        private const val IllegalArgumentException = "gql: IllegalArgumentException"
    }

}