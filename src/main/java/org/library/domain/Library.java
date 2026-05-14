package org.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Library {
    private List<Item> itemsInStore;
    private List<Item> borrowedItems;
    private List<Item> lostItems;

    /**
     * permits the user to borrow items
     *
     * @param item the item to be borrowed
     * @param user the user borrowing the item
     */
    public void borrowItems(Item item, User user) {
        if (user instanceof Student) {
            if (!(item instanceof Book)) {
                System.out.println("Students can only borrow books");
            }

            try {
                if (user.userBorrowedItems.size() == 5) {
                    throw new RuntimeException("Students can only borrow up to 5 books");
                } else if (!itemsInStore.contains(item)) {
                    throw new RuntimeException("No copies left");
                } else {
                    itemsInStore.remove(item);
                    user.userBorrowedItems.add(item);
                    borrowedItems.add(item);
                }
            } catch (RuntimeException e) {
                e.getMessage();
            }
        }

        if (user instanceof Teacher) {
            try {
                if (user.userBorrowedItems.size() == 10) {
                    throw new RuntimeException("Teachers can only borrow up to 10 items");
                } else {
                    itemsInStore.remove(item);
                    user.userBorrowedItems.add(item);
                    borrowedItems.add(item);
                }
            } catch (RuntimeException e) {
                e.getMessage();
            }
        }

        if (user instanceof Admin) {
            itemsInStore.remove(item);
            user.userBorrowedItems.add(item);
            borrowedItems.add(item);
        }
    }

    /**
     * permits the user to return an item
     *
     * @param item the item to be returned
     * @param user the user returning the item
     */
    public void returnItems(Item item, User user) {
        user.userBorrowedItems.remove(item);
        borrowedItems.remove(item);
        itemsInStore.add(item);
    }
}
