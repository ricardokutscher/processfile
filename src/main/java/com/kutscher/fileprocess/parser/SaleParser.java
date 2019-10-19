package com.kutscher.fileprocess.parser;

import com.kutscher.fileprocess.dto.Item;
import com.kutscher.fileprocess.dto.Sale;
import com.kutscher.fileprocess.utils.LineParser;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

@Component
public class SaleParser implements LineFileParser {

    private LineParser lineParser;

    public SaleParser(LineParser lineParser) {
        this.lineParser = lineParser;
    }

    @Override
    public Optional<Object> parse(String line) {
        Matcher matcher = lineParser.getToken(line, LineParser.EXPRESSION_IS_SALE);
        return Optional.of(Sale.builder()
                .salesName(matcher.group("salesman"))
                .saleId(matcher.group("saleId"))
                .sales(getItens(line))
                .totalOrder(
                        getItens(line).stream()
                                .map(item -> item.getValue().multiply(new BigDecimal(item.getQuantity())))
                                .reduce((x, y) -> x.add(y)).get())
                .build());
    }

    public List<Item> getItens(String line){
        List<Item> list = new ArrayList<Item>();
        Matcher matchers = lineParser.getAllTokens(line, LineParser.EXPRESSION_GET_ATTRIBUTES_ITEM);
        while(matchers.find()){
            list.add(Item.builder()
                    .itemId(Integer.valueOf(matchers.group("itemId")))
                    .quantity(Integer.valueOf(matchers.group("quantity")))
                    .value(new BigDecimal(matchers.group("value")))
                    .build());
        }
        return list;
    }

    @Override
    public Boolean isValid(String line) {
        return lineParser.verifyExpression(line, LineParser.EXPRESSION_IS_SALE);
    }
}