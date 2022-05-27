package com.github.savitoh;

import java.io.IOException;

public interface ApiClient {

  String getBody() throws IOException, InterruptedException;
}
