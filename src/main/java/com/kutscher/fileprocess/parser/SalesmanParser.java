package com.kutscher.fileprocess.parser;

import com.kutscher.fileprocess.dto.Salesman;
import com.kutscher.fileprocess.utils.LineParser;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Matcher;

@Component
public class SalesmanParser implements LineFileParser {

    private LineParser lineParser;

    public SalesmanParser(LineParser lineParser) {
        this.lineParser = lineParser;
    }

    @Override
    public Optional<Object> parse(String line) {
        Matcher matcher = lineParser.getToken(line, LineParser.EXPRESSION_IS_SALESMAN);
        return Optional.of(Salesman.builder()
                .cpf(matcher.group("taxId"))
                .name(matcher.group("name"))
                .salary(new BigDecimal(matcher.group("salary")))
                .build());
    }

    @Override
    public Boolean isValid(String line) {
        return lineParser.verifyExpression(line, LineParser.EXPRESSION_IS_SALESMAN);
    }
}