package gql.util

import gql.LegacyGql
import gql.Gql
import java.lang.Exception

class LegacyGqlException(message: String): LegacyGql() {
    init { throw Exception(message) }
}

class GqlException(message: String): Gql() {
    init { throw Exception(message) }
}