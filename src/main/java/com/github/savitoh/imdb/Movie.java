package com.github.savitoh.imdb;

import com.github.savitoh.Content;

public record Movie(String title, String urlImage, String rating, String year) implements Content {}
