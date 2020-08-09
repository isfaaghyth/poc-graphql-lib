package gql

import gql.util.FileUtil
import org.junit.Assert
import org.junit.Test
import query.QueryParams
import query.QueryParameterParser

class QueryParameterParserTest {

    private val query = FileUtil.readFile("/sample.gql")

    @Test fun `should separate parameters correctly`() {
        val givenValue = "\$param: String"
        val expectedValue = Pair("param", "String")
        val result = QueryParameterParser.separateParams(givenValue)
        Assert.assertEquals(result, expectedValue)
    }

    @Test fun `should return param1 as variable and String as data type of param1`() {
        val expectedValue = arrayOf(QueryParams("param1", "String"))
        val result = QueryParameterParser.parameters(query)
        Assert.assertEquals(result.first().variable, expectedValue.first().variable)
        Assert.assertEquals(result.first().type, expectedValue.first().type)
    }

}