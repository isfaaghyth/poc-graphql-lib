package gql

import gql.network.RequestBuilder
import gql.query.QueryParameterParser
import gql.util.GqlException

open class Gql {

    private lateinit var requestBuilder: RequestBuilder
    private val params = mutableMapOf<String, Any>()

    fun queries(rawQuery: String) = apply {
        requestBuilder = RequestBuilder(rawQuery)
        collectParameters(rawQuery)
    }

    fun parameters(gqlParams: Map<String, Any>) = apply {
        if (gqlParams.size != params.size) return GqlException(ArrayIndexOutOfBoundsException)

        // validate query keys
        if (!gqlParams.keys.containsAll(params.keys)) {
            return GqlException("$ObjectNotFoundException a few parameters not found")
        }

        // validate variable data type
        params.keys.forEach {
            if (Class.forName(params[it] as String?) != gqlParams[it]?.javaClass) {
                return GqlException("$IllegalArgumentException ${gqlParams[it]} is not ${params[it]}")
            }
        }

        // valid
        requestBuilder.parameters(gqlParams)
    }

    private fun collectParameters(query: String) = apply {
        params.clear()
        params.putAll(QueryParameterParser.parameters(query))
    }

    companion object {
        private const val ArrayIndexOutOfBoundsException = "ArrayIndexOutOfBoundsException:"
        private const val IllegalArgumentException = "IllegalArgumentException:"
        private const val ObjectNotFoundException = "ObjectNotFoundException:"
    }

}