package com.github.savitoh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.savitoh.utils.HashUtils;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;

class HashUtilsTest {

  @Test
  void Should_TrowsException_WhenValueIsNull() {
    Exception exception =
        assertThrows(
            NullPointerException.class,
            () -> {
              HashUtils.generateMD5Hash(null);
            });

    assertEquals("'value' cannot be null.", exception.getMessage());
  }

  @Test
  void Should_ReturnMD5Hash_FromValue() throws NoSuchAlgorithmException {
    var value = "165272753900023975270ff03a7c4384da3bec54016e5";

    var hash = HashUtils.generateMD5Hash(value);

    assertEquals("b5611387b7aeb03b56e089e83b14ec20", hash);
  }
}
