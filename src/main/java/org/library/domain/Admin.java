package org.library.domain;

import lombok.*;
import org.library.interfaces.Reportable;

@ToString(callSuper = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Admin extends User implements Reportable {
    public Admin(String name) {
        super(name);
    }

    @Override
    public void reportInStore() {
        for (Item item : Library.items) {
            if (item.getStatus().equals(Item.Status.AVAILABLE)) {
                System.out.println(item.getId() + "-" + item.getTitle() + ": Available");
            }
        }
    }

    @Override
    public void reportBorrowed() {
        for (Item item : Library.items) {
            if (item.getStatus().equals(Item.Status.BORROWED)) {
                System.out.println(item.getId() + "-" + item.getTitle() + ": Borrowed");
            }
        }
    }

    @Override
    public void reportLost() {
        for (Item item : Library.items) {
            if (item.getStatus().equals(Item.Status.LOST)) {
                System.out.println(item.getId() + "-" + item.getTitle() + ": Borrowed");
            }
        }
    }

    @Override
    public void reportALl() {
        for (Item item : Library.items) {
            if (item.getStatus().equals(Item.Status.AVAILABLE)) {
                System.out.println(item.getId() + "-" + item.getTitle() + ": Available");
            }

            if (item.getStatus().equals(Item.Status.BORROWED)) {
                System.out.println(item.getId() + "-" + item.getTitle() + ": Borrowed");
            }

            if (item.getStatus().equals(Item.Status.LOST)) {
                System.out.println(item.getId() + "-" + item.getTitle() + ": Lost");
            }
        }
    }

    /**
     * backs up all the users and books into another file each
     */
    public void backup() {
        Library.loadUsers();
        Library.loadBooks();
    }
}
