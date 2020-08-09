package gql

fun main() {
    new()
}

fun new() {
    Gql().setUrl("https://metaphysics-production.artsy.net/")
        .queries(fakeQuery())
        .request { response ->
            if (response != null) {
                println(response)
            }
        }
}

fun fakeQuery(): String {
    return """
        {
          popular_artists {
            artists {
              name
            }
          }
        }
    """.trimIndent()
}