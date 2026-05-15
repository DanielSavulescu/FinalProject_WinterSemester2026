package org.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public abstract class User implements Comparable<User> {
    protected String id;
    @Setter
    protected String name;
    protected List<Item> userBorrowedItems;

    private static int nextId = 1;

    public User(String name) {
        this.id = String.format("%04d", nextId++);
        this.name = name;
        this.userBorrowedItems = new ArrayList<>();
    }

    @Override
    public int compareTo(User o) {
        return id.compareTo(o.id) == 0 ? name.compareToIgnoreCase(o.name) : id.compareTo(o.id);
    }
}
