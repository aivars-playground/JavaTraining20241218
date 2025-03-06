package org.example.mypackage;

import java.util.Objects;

public class Word {
    private String word;

    public Word(String word) {
        this.word = word;
    }

    public static Word of(String word) {
            return new Word(word);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(word);
    }
}
