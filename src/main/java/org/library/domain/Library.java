package org.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Library {
    public static List<Item> items;

    /**
     * permits the user to borrow items
     *
     * @param item the item to be borrowed
     * @param user the user borrowing the item
     */
    public void borrowItems(Item item, User user) throws IllegalArgumentException {
        if (!items.contains(item)) {
            throw new IllegalArgumentException("Item is currently unavailable");
        }

        if (user instanceof Student) {
            if (!(item instanceof Book)) {
                throw new IllegalArgumentException("Students can only borrow books");
            }
            if (user.getUserBorrowedItems().size() >= 5) {
                throw new IllegalArgumentException("Student borrow limit reached");
            }
        } else if (user instanceof Teacher) {
            if (user.getUserBorrowedItems().size() >= 10) {
                throw new IllegalArgumentException("Teacher borrow limit reached");
            }
        }

        items.remove(item);
        user.userBorrowedItems.add(item);
        item.setStatus(Item.Status.BORROWED);
    }

    /**
     * permits the user to return an item
     *
     * @param item the item to be returned
     * @param user the user returning the item
     */
    public void returnItems(Item item, User user) {
        user.userBorrowedItems.remove(item);
        items.add(item);
        item.setStatus(Item.Status.AVAILABLE);
    }

    public List<Item> searchStream(String keyWord) {
        String lowerCaseKey = keyWord.toLowerCase();

        List<Item> searchedItems = new ArrayList<>(items);

        return searchedItems.stream()
                .filter(item -> {
                    boolean authorContainsKeyWord = false;
                    if (item instanceof Book) {
                        authorContainsKeyWord = ((Book) item).getAuthor().toLowerCase().contains(lowerCaseKey);
                    }
                    return item.getTitle().toLowerCase().contains(lowerCaseKey) || authorContainsKeyWord;
                })
                .toList();
    }
}
