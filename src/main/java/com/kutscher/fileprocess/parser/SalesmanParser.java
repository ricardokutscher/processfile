package com.kutscher.fileprocess.parser;

import com.kutscher.fileprocess.dto.Salesman;
import com.kutscher.fileprocess.utils.LineParser;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class SalesmanParser implements LineFileParser {

    private LineParser lineParser;

    public SalesmanParser(LineParser lineParser) {
        this.lineParser = lineParser;
    }

    @Override
    public Optional<Object> parse(String line) {
        return Optional.of(Salesman.builder()
                .cpf(lineParser.getResult().group("taxId"))
                .name(lineParser.getResult().group("name"))
                .salary(new BigDecimal(lineParser.getResult().group("salary")))
                .build());
    }

    @Override
    public Boolean isValid(String line) {
        return lineParser.verifyExpression(line, LineParser.EXPRESSION_IS_SALESMAN);
    }
}