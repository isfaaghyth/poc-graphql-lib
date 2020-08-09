package gql

fun main() {
    new()
}

fun new() {
    Gql().queries(fakeQuery())
        .parameters(mapOf(
            "param1" to "Isfa",
            "param2" to 23,
            "param3" to true
        ))
}

fun fakeQuery(): String {
    val param1 = "param1"
    val param2 = "param2"
    val param3 = "param3"

    return """
            mutation test_isfa_ganteng($$param1: String, $$param2: Int, $$param3: Boolean) {
                test_isfa_ganteng(param1: $$param1, param2: $$param2, param3: $$param3) {
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
    """.trimIndent()
}