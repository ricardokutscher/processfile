package com.kutscher.fileprocess.parser;

import com.kutscher.fileprocess.dto.Customer;
import com.kutscher.fileprocess.utils.LineParser;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;

@Component
public class CustomerParser implements LineFileParser {

    private LineParser lineParser;

    public CustomerParser(LineParser lineParser) {
        this.lineParser = lineParser;
    }

    @Override
    public Optional<Object> parse(String line) {
        Matcher matcher = lineParser.getToken(line, LineParser.EXPRESSION_IS_CUSTOMER);
        return Optional.of(Customer.builder()
                .CNPJ(matcher.group("taxId"))
                .name(matcher.group("name"))
                .businessArea(matcher.group("businessArea"))
                .build());
    }

    @Override
    public Boolean isValid(String line) {
        return lineParser.verifyExpression(line, LineParser.EXPRESSION_IS_CUSTOMER);
    }
}
