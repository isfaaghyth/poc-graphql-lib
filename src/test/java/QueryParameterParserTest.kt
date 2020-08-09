import org.junit.Assert
import org.junit.Test
import util.FileUtil

class QueryParameterParserTest {

    private val pattern = "\\\$[A-Za-z]+(\\\\.[A-Za-z]+)*: (?:String|Int|Boolean)"
    private val regex = Regex(pattern)

    private val query = FileUtil.readFile("/sample.gql")

    @Test fun `should separate parameters correctly`() {
        val givenValue = "\$param: String"
        val expectedValue = Pair("param", "String")
        val split = givenValue.split(":")
        val result = Pair(split[0].drop(1).trim(), split[1].trim())
        Assert.assertEquals(result, expectedValue)
    }

    @Test fun `should return parameters`() {
        val expectedValue = arrayOf(
            QueryParams("param1", "String"),
            QueryParams("param2", "Int"),
            QueryParams("param3", "Boolean")
        )

        println(regex.find(query))
    }

}