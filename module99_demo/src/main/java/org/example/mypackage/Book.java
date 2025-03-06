package org.example.mypackage;

import java.util.List;
import java.util.Optional;

public class Book {
    private List<Page> pages;

    public Book(List<Page> pages) {
        this.pages = pages;
    }

    public Optional<Integer> findFirst(Word word) {
        int page = 1;
        var iteror = pages.iterator();
        while (iteror.hasNext()) {
            var currentPage = iteror.next();
            if (currentPage.contains(word)) {
                return Optional.of(page);
            } else {
                page++;
            }
        }
        return Optional.empty();
    }
}
