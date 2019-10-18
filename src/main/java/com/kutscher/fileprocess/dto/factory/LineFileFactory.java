package com.kutscher.fileprocess.dto.factory;

import com.kutscher.fileprocess.parser.CustomerParser;
import com.kutscher.fileprocess.parser.LineFileParser;
import com.kutscher.fileprocess.parser.SaleParser;
import com.kutscher.fileprocess.parser.SalesmanParser;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class LineFileFactory {

    private CustomerParser customerParser;
    private SalesmanParser salesmanParser;
    private SaleParser saleParser;

    public LineFileFactory(CustomerParser customerParser, SalesmanParser salesmanParser, SaleParser saleParser) {
        this.customerParser = customerParser;
        this.salesmanParser = salesmanParser;
        this.saleParser = saleParser;
    }

    public Stream<LineFileParser> get() {
        return Stream.of(customerParser, salesmanParser, saleParser);
    }

}
