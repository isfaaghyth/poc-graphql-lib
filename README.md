### summary
created lightweight graphql network lib for `kotlin/jvm` okhttp based and `kotlin/native` ktor based.

### prerequirement
- graphql has a query and runs on by it
- the query has parameters with different data types

### preaction
- identifiers any parameters from string query

### todo
- [ ] identifier of parameters from graphql query
- [ ] convert `plain-text data` type into `data type object`
- [ ] create graphql query parameters builder
- [ ] ...

### phase 1: graphql query builder
`query_sample.json`
```
mutation test_isfa_ganteng($name: String, $age: Int, $verified: Boolean) {
    test_isfa_ganteng(name: $name, age: $age, verified: $verified) {
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
    .queries(fakeQuery())
    .parameters(arrayOf(
        "Muh Isfhani Ghiath",
        23,
        true
    ))
```