package com.kutscher.fileprocess.parser;

import java.util.Optional;

public interface LineFileParser {

    Optional<Object> parse(String line);
    Boolean isValid(String line);

}
