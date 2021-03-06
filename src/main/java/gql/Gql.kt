package gql

import gql.network.OkhttpBuilder
import gql.network.RequestBuilder
import gql.query.ParameterParser
import gql.util.GqlException

open class Gql {

    private lateinit var url: String
    private lateinit var gqlQuery: String
    private val params = mutableMapOf<String, Any>()
    private val network by lazy { OkhttpBuilder() }
    private val requestBuilder by lazy { RequestBuilder(gqlQuery) }
    var onError: ((Throwable) -> Unit) = {}

    fun setUrl(mainUrl: String) = apply {
        url = mainUrl
    }

    fun query(rawQuery: String) = apply {
        gqlQuery = rawQuery
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

    fun request(response: (String) -> Unit) = apply {
        if (!::url.isInitialized) return GqlException("$UrlNotFoundException you haven't set main url yet.")
        if (!::gqlQuery.isInitialized) return GqlException("$QueryNotFoundException you haven't set gql query yet.")

        network.post(url, requestBuilder.build(), {
            it?.let { res -> response(res) }
        }, {
            onError(it)
        })
    }

    fun error(error: (Throwable) -> Unit) = apply { onError = error }

    private fun collectParameters(query: String) = apply {
        params.clear()
        params.putAll(ParameterParser.parse(query))
    }

    companion object {
        private const val ArrayIndexOutOfBoundsException = "ArrayIndexOutOfBoundsException:"
        private const val IllegalArgumentException = "IllegalArgumentException:"
        private const val ObjectNotFoundException = "ObjectNotFoundException:"
        private const val QueryNotFoundException = "QueryNotFoundException:"
        private const val UrlNotFoundException = "UrlNotFoundException:"
    }

}