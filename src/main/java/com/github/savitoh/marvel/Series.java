package com.github.savitoh.marvel;

import com.github.savitoh.Content;

public record Series(String title, String urlImage, String rating, String year)
    implements Content {}
