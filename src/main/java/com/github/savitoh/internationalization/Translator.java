package com.github.savitoh.internationalization;

import java.util.Locale;
import java.util.Optional;

public sealed interface Translator permits ResourceBundleTranslator {

  Optional<String> translate(Locale locale, String tag);
}
