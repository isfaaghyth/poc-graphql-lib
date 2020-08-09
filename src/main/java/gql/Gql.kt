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
        if (gqlParams.size != params.size) return GqlException("jumlah param nya beda")
        gqlParams.toList().forEachIndexed { index, any ->
            any?.let {
                if (it.javaClass != Class.forName(params[index].type)) {
                    return GqlException("tipe data nya beda")
                }
            }
        }
    }

    private fun collectParameters() = apply {
        params.clear()
        params.addAll(QueryParameterParser.parameters(query))
    }

}