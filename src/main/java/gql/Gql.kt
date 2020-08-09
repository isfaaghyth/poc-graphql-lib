package gql

import gql.query.QueryParameterParser
import gql.util.GqlException

open class Gql {

    private val params = mutableMapOf<String, Any>()
    private lateinit var query: String

    fun queries(rawQuery: String) = apply {
        query = rawQuery
        collectParameters()
    }

    fun parameters(gqlParams: Map<String, Any>) = apply {
        if (gqlParams.size != params.size) return GqlException(ArrayIndexOutOfBoundsException)

        // validate query keys
        if (!gqlParams.keys.containsAll(params.keys)) {
            return GqlException("$ClassCastException oldParameters not exact same")
        }

        // validate variable data type
        params.keys.forEach {
            if (Class.forName(params[it] as String?) != gqlParams[it]?.javaClass) {
                return GqlException("$IllegalArgumentException ${gqlParams[it]} is not ${params[it]}")
            }
        }
    }

    private fun collectParameters() = apply {
        params.clear()
        params.putAll(QueryParameterParser.parameters(query))
    }

    companion object {
        private const val ArrayIndexOutOfBoundsException = "ArrayIndexOutOfBoundsException:"
        private const val IllegalArgumentException = "IllegalArgumentException:"
        private const val ClassCastException = "ClassCastException:"
    }

}