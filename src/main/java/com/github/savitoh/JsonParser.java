package com.github.savitoh;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface JsonParser {

  List<? extends Content> parse() throws JsonProcessingException;
}
