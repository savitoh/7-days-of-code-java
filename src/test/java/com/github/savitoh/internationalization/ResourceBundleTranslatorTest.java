package com.github.savitoh.internationalization;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

class ResourceBundleTranslatorTest {

  private final Translator translator = new ResourceBundleTranslator();

  @Test
  void Should_ThrowException_When_LocaleIsNull() {
    Exception exception =
        assertThrows(NullPointerException.class, () -> translator.translate(null, ""));

    assertEquals("'locale' cannot be null.", exception.getMessage());
  }

  @Test
  void Should_ThrowException_When_TagIsNull() {
    Exception exception =
        assertThrows(NullPointerException.class, () -> translator.translate(Locale.US, null));

    assertEquals("'tag' cannot be null.", exception.getMessage());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/parameters-test/translations.csv")
  void Should_ReturnTranslation_When_TagExistsOnResourcesBundle(String tag, String translation) {
    Locale locale = Locale.forLanguageTag("pt-BR");
    Optional<String> maybeTranslation = translator.translate(locale, tag);

    assertTrue(maybeTranslation.isPresent());
    assertEquals(translation, maybeTranslation.get());
  }

  @ParameterizedTest
  @CsvSource({"FAKE_TAG", "Empty_TAG", "RANDOM_TAG"})
  void Should_ReturnEmptyTranslation_When_NotExistsTagOnResourcesBundle(String tag) {
    Locale locale = Locale.forLanguageTag("pt-BR");
    Optional<String> maybeTranslation = translator.translate(locale, tag);

    assertTrue(maybeTranslation.isEmpty());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/parameters-test/default_translations.csv")
  void Should_ReturnDefaultTranslation_When_NotExistsResourcesBundleForLocale(
      String tag, String translation) {
    Locale locale = Locale.forLanguageTag("de-DE");
    Optional<String> maybeTranslation = translator.translate(locale, tag);

    assertTrue(maybeTranslation.isPresent());
    assertEquals(translation, maybeTranslation.get());
  }
}
