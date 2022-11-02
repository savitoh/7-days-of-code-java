package com.github.savitoh.marvel;

import com.github.savitoh.utils.HashUtils;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

final class UriBuilder {

  private final String baseUri;
  private final String publicApiKey;
  private final String privateApiKey;
  private final long timestamp;

  UriBuilder(String baseUri, String publicApiKey, String privateApiKey) {
    this.baseUri = baseUri;
    this.publicApiKey = publicApiKey;
    this.privateApiKey = privateApiKey;
    this.timestamp = Instant.now().toEpochMilli();
  }

  String builder() throws NoSuchAlgorithmException {
    final String uriTemplate = baseUri + "?ts=%s&apikey=%s&hash=%s&limit=100";
    return uriTemplate.formatted(timestamp, publicApiKey, this.generateMD5Hash());
  }

  private String generateMD5Hash() throws NoSuchAlgorithmException {
    return HashUtils.generateMD5Hash(timestamp + privateApiKey + publicApiKey);
  }
}
