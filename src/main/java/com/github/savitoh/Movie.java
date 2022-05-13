package com.github.savitoh;

public record Movie(String title, String urlImage, String rating, String year) implements Content {}
