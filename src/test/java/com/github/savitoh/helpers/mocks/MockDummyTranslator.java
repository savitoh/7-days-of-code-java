package com.github.savitoh.helpers.mocks;

import com.github.savitoh.internationalization.ResourceBundleTranslator;
import java.util.Locale;
import java.util.Optional;

public class MockDummyTranslator extends ResourceBundleTranslator {

  @Override
  public Optional<String> translate(Locale locale, String tag) {
    return Optional.empty();
  }
}
