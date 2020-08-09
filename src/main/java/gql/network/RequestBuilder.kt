package gql.network

import org.json.JSONObject

class RequestBuilder(rawQuery: String) {

    private val query = "query"
    private val variables = "variables"

    private val request = JSONObject()
    private val parameters = JSONObject()

    init { request.put(query, rawQuery) }

    fun parameters(params: Map<String, Any>) = apply {
        params.keys.forEach {
            parameters.put(it, params[it])
        }
    }

    fun build(): String {
        request.put(variables, parameters)
        return request.toString()
    }

}