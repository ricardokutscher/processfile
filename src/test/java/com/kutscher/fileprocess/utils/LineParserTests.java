package com.kutscher.fileprocess.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.regex.Matcher;

import static junit.framework.TestCase.*;

@RunWith(MockitoJUnitRunner.class)
public class LineParserTests {

    @InjectMocks
    private LineParser lineParser;

    @Test
    public void verifyExpressionForSalesmanWithValidLineReturnTrue(){
        Boolean result = lineParser.verifyExpression("001ç12345678901çDiegoç50000", LineParser.EXPRESSION_IS_SALESMAN);

        assertTrue(result);
    }

    @Test
    public void verifyExpressionForSalesmanWithValidLineReturnFalse(){
        Boolean result = lineParser.verifyExpression("123ç12345678901çDiegoç50000", LineParser.EXPRESSION_IS_SALESMAN);

        assertFalse(result);
    }

    @Test
    public void verifyExpressionForCustomerWithValidLineReturnTrue(){
        Boolean result = lineParser.verifyExpression("002ç09876543210987çEduardoPereiraçRural", LineParser.EXPRESSION_IS_CUSTOMER);

        assertTrue(result);
    }

    @Test
    public void verifyExpressionForCustomerWithValidLineReturnFalse(){
        Boolean result = lineParser.verifyExpression("001ç09876543210987çEduardoPereiraçRural", LineParser.EXPRESSION_IS_CUSTOMER);

        assertFalse(result);
    }

    @Test
    public void verifyExpressionForSaleWithValidLineReturnTrue(){
        Boolean result = lineParser.verifyExpression("003ç01ç[1-34-210,2-33-1.50,3-40-0.10]çJoao", LineParser.EXPRESSION_IS_SALE);

        assertTrue(result);
    }

    @Test
    public void verifyExpressionForSaleWithValidLineReturnFalse(){
        Boolean result = lineParser.verifyExpression("003ç01ç[1-34-210,2-33-1.50,3-40-0.10]", LineParser.EXPRESSION_IS_SALE);

        assertFalse(result);
    }

    @Test
    public void getResultForSalesmanWithValidLineReturnTrue(){
        lineParser.verifyExpression("001ç12345678901çDiegoç50000", LineParser.EXPRESSION_IS_SALESMAN);

        Matcher result = lineParser.getToken("001ç12345678901çDiegoç50000", LineParser.EXPRESSION_IS_SALESMAN);

        assertEquals(result.group("taxId"), "12345678901");
        assertEquals(result.group("name"), "Diego");
        assertEquals(result.group("salary"), "50000");
    }

    @Test
    public void getResultForSaleWithValidLineReturnTrue(){
        lineParser.verifyExpression("003ç01ç[1-34-210,2-33-1.50,3-40-0.10]çJoao", LineParser.EXPRESSION_IS_SALE);

        Matcher result = lineParser.getToken("003ç01ç[1-34-210,2-33-1.50,3-40-0.10]çJoao", LineParser.EXPRESSION_IS_SALE);

        assertEquals(result.group("saleId"), "01");
        assertEquals(result.group("salesman"), "Joao");
    }

    @Test
    public void getResultForCustomerWithValidLineReturnTrue(){
        lineParser.verifyExpression("002ç09876543210987çEduardoPereiraçRural", LineParser.EXPRESSION_IS_CUSTOMER);

        Matcher result = lineParser.getToken("002ç09876543210987çEduardoPereiraçRural", LineParser.EXPRESSION_IS_CUSTOMER);

        assertEquals(result.group("taxId"), "09876543210987");
        assertEquals(result.group("name"), "EduardoPereira");
        assertEquals(result.group("businessArea"), "Rural");
    }

}

