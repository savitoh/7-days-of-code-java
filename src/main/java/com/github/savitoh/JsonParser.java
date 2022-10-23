package com.github.savitoh;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface JsonParser {

  List<Content> parse() throws JsonProcessingException;
}
