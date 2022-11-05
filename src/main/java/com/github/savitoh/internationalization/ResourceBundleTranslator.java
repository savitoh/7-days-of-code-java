package com.github.savitoh.internationalization;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public non-sealed class ResourceBundleTranslator implements Translator {

  private static final Logger LOGGER = Logger.getLogger(ResourceBundleTranslator.class.getName());

  private static final String I18N_PATH = "localization/i18n";

  @Override
  public Optional<String> translate(Locale locale, String tag) {
    Objects.requireNonNull(locale, "'locale' cannot be null.");
    Objects.requireNonNull(tag, "'tag' cannot be null.");
    final var resourceBundle = ResourceBundle.getBundle(I18N_PATH, locale);
    if (resourceBundle.containsKey(tag)) {
      resourceBundle.getString(tag);
      return Optional.of(resourceBundle.getString(tag));
    }
    final String message =
        "Tag: '%s' not found on Resource Bundle to Locale: '%s'".formatted(tag, locale.toString());
    LOGGER.log(Level.INFO, message);
    return Optional.empty();
  }
}
