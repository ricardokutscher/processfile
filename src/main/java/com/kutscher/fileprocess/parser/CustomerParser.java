package com.kutscher.fileprocess.parser;

import com.kutscher.fileprocess.dto.Customer;
import com.kutscher.fileprocess.utils.LineParser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerParser implements LineFileParser {

    private LineParser lineParser;

    public CustomerParser(LineParser lineParser) {
        this.lineParser = lineParser;
    }

    @Override
    public Optional<Object> parse(String line) {
        return Optional.of(Customer.builder()
                .CNPJ(lineParser.getResult().group("taxId"))
                .name(lineParser.getResult().group("name"))
                .businessArea(lineParser.getResult().group("businessArea"))
                .build());
    }

    @Override
    public Boolean isValid(String line) {
        return lineParser.verifyExpression(line, LineParser.EXPRESSION_IS_CUSTOMER);
    }
}
