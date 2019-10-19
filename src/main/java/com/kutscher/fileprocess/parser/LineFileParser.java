package com.kutscher.fileprocess.parser;

import java.util.Optional;
import java.util.regex.Matcher;

public interface LineFileParser {

    Optional<Object> parse(String line);
    Boolean isValid(String line);

}
