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
public abstract class User {
    protected String id;
    @Setter
    protected String name;
    @Setter
    protected List<Item> userBorrowedItems;

    private static int nextId = 1;

    public User(String name) {
        this.id = String.format("%04d", nextId++);
        this.name = name;
        this.userBorrowedItems = new ArrayList<>();
    }
}
