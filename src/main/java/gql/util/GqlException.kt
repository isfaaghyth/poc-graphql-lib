package gql.util

import gql.Gql
import java.lang.Exception

class GqlException(message: String): Gql() {
    init { throw Exception(message) }
}