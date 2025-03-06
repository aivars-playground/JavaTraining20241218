package org.example.mypackage;

import java.util.List;

public class Page {
    private List<Word> words;

    public Page(List<Word> words) {
        this.words = words;
    }

    public boolean contains(Word word) {
        return words.contains(word);
    }
}
