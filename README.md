### summary
created lightweight graphql network lib for `kotlin/jvm` okhttp based and `kotlin/native` ktor based.

### prerequirement
- graphql has a query and runs on by it
- the query has parameters with different data types

### preaction
- identifiers any parameters from string query

### todo
- [x] identifier of parameters from graphql query
- [x] convert `plain-text data` type into `data type object` (cancelled)
- [x] create graphql query parameters builder
- [x] create request builder for graphql
- [x] create okhttp network builder
- [ ] onError

### phase 1: graphql query builder
`query_sample.json`
```
mutation employee($name: String, $age: Int, $verified: Boolean) {
    employee(name: $name, age: $age, verified: $verified) {
        status
        data {
            name
            avatar
            phone
            address {
                zip
                province
            }
        }
    }
}
```
`Test.kt`
```kt
Gql()
    .query("query_sample.json")
    .parameters(mapOf(
        "name" to "Muh Isfhani Ghiath",
        "age" to 23,
        "verified" to true
    ))
    .request { response ->
        if (response != null) {
            // yey!
        }
    }
```

### phase 2: network poc
`Test.kt`
```kt
Gql()
    .url("https://isfa.test/")
    .query("query_sample.json")
    .parameters(mapOf(
        "name" to "Muh Isfhani Ghiath",
        "age" to 23,
        "verified" to true
    ))
    .request { response ->
        if (response != null) {
            // yey!
        }
    }
    .error { throwable ->
        // oops!
    }
```