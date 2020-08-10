package gql

fun main() {
    new()
}

fun new() {
    Gql().setUrl("https://metaphysics-production.artsy.net/")
        .query(fakeQuery())
        .request { response ->
            println(response)
        }.error {
            println(it.localizedMessage)
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