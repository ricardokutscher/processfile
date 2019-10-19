package com.kutscher.fileprocess.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains all regular expressions to find specific scenario
 * @author kutscher
 *
 */
@Component
public class LineParser {
	
	/**
	 * Expression to valid a line with salesman
	 * ex: 001ç11122233344çRenatoç40000.99
	 */
	public static final String EXPRESSION_IS_SALESMAN = "^(?<id>001)ç(?<taxId>[0-9]{11})ç(?<name>[\\s\\S]+)ç(?<salary>[0-9]*\\.?[0-9]+)";

	/**
	 * Expression to valid a line with customer
	 * ex: 002ç2345675434544345çJosedaSilvaçRural
	 */
	public static final String EXPRESSION_IS_CUSTOMER = "^(?<id>002)ç(?<taxId>[0-9]{14})ç(?<name>[\\s\\S]+)ç(?<businessArea>[\\s\\S]+)";

	/**
	 * Expression to valid a line with sale
	 * ex: 003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato
	 */
	public static final String EXPRESSION_IS_SALE = "^(?<id>003)ç(?<saleId>[0-9]{0,})ç(?<itens>\\[[0-9\\-\\,\\.]+\\])ç(?<salesman>[\\s\\S]+)";

	/**
	 * Expression to get the values of the Item in a sale
	 * Entry: [1-34-10 || 2-33-1.50 || 3-40-0.10]
	 * Return: {1, 34, 10} || {2, 33, 1.50} || {3, 40, 0.10}
	 */
	public static final String EXPRESSION_GET_ATTRIBUTES_ITEM = "((?<itemId>[0-9]+)-(?<quantity>[0-9]{0,9}+)-(?<value>[0-9]*\\.?[0-9]+))+";

	/**
	 * Return if is a valid string and set the this.result with the groups
	 * @param line
	 * @param expression
	 * @return
	 */
	public Boolean verifyExpression(String line, String expression) {
		try {
			Pattern pattern = Pattern.compile(expression);
			if (pattern.matcher(line).find()) {
				return true;
			}
		}catch (Exception e) {
			System.out.println("Regular expression did not match");
		}
		return false;
	}

	/**
	 * Returns the tokens found in the string.
	 * @param line
	 * @param expression
	 * @return
	 */
	public Matcher getAllTokens(String line, String expression) {
		Pattern pattern = Pattern.compile(expression);
		return pattern.matcher(line);
	}


	/**
	 * Returns the tokens found in the string.
	 * @param line
	 * @param expression
	 * @return
	 */
	public Matcher getToken(String line, String expression) {
		Pattern pattern = Pattern.compile(expression);
		Matcher result = pattern.matcher(line);
		result.find();
		return result;
	}
}
