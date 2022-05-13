package com.github.savitoh;

import java.util.List;

public interface JsonParser {

  List<? extends Content> parse();
}
