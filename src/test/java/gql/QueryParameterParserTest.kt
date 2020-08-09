package gql

import gql.util.FileUtil
import org.junit.Assert
import org.junit.Test
import gql.entity.QueryParams
import gql.query.QueryParameterParser

class QueryParameterParserTest {

    private val query = FileUtil.readFile("/sample.gql")

    @Test fun `should separate parameters correctly`() {
        val givenValue = "\$param: String"
        val expectedValue = Pair("param", "java.lang.String")
        val result = QueryParameterParser.separateParams(givenValue)
        Assert.assertEquals(result, expectedValue)
    }

    @Test fun `should return param1 as variable and String as data type of param1`() {
        val expectedValue = arrayOf(QueryParams("param1", "java.lang.String"))
        val result = QueryParameterParser.parameters(query)
        println(result)
        Assert.assertEquals(result.first().variable, expectedValue.first().variable)
        Assert.assertEquals(result.first().type, expectedValue.first().type)
    }

    @Test fun `validate variable data type with java reflection`() {
        val string = Class.forName("java.lang.String")
        val integer = Class.forName("java.lang.Integer")
        val boolean = Class.forName("java.lang.Boolean")
        Assert.assertEquals(string.kotlin, String::class)
        Assert.assertEquals(integer.kotlin, Int::class)
        Assert.assertEquals(boolean.kotlin, Boolean::class)
    }

    @Test fun `verified variable data type`() {
        assert(1.javaClass == Int::class.java)
    }

}