package org.library.service;

import org.library.domain.Book;

public class Validation {
    /**
     * validates if a book has the correct ISBN structure
     * @param book the book to be checked
     * @return true if the ISBN corresponds to the good structure, false if not
     */
    public static boolean validation(Book book) {
        if (book.getIsbn() == null) {
            return false;
        }

        for (Character c : book.getIsbn().toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return book.getIsbn().length() == 13;
    }
}
