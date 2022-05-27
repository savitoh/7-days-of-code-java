package com.github.savitoh.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public final class HashUtils {

  private HashUtils() {}

  public static String generateMD5Hash(String value) throws NoSuchAlgorithmException {
    Objects.requireNonNull(value, "'value' cannot be null.");
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));
    return new BigInteger(1, digest).toString(16);
  }
}
