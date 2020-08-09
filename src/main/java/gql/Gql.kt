package gql

import gql.network.RequestBuilder
import gql.query.QueryParameterParser
import gql.util.GqlException

open class Gql {

    private lateinit var url: String

    private lateinit var requestBuilder: RequestBuilder
    private val params = mutableMapOf<String, Any>()

    fun setUrl(mainUrl: String) = apply {
        url = mainUrl
    }

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

    fun request(result: (String) -> Unit) = apply {
        if (!::url.isInitialized) {
            return GqlException("$UrlNotFoundException you haven't set main url yet.")
        }

        result("berhasil!")
    }

    private fun collectParameters(query: String) = apply {
        params.clear()
        params.putAll(QueryParameterParser.parameters(query))
    }

    companion object {
        private const val ArrayIndexOutOfBoundsException = "ArrayIndexOutOfBoundsException:"
        private const val IllegalArgumentException = "IllegalArgumentException:"
        private const val ObjectNotFoundException = "ObjectNotFoundException:"
        private const val UrlNotFoundException = "UrlNotFoundException:"
    }

}