package org.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public abstract class Item {
    protected String id;
    @Setter
    protected String title;
    @Setter
    protected Status status;

    private static int nextId = 1;

    public Item(String title, Status status) {
        this.id = String.format("%04d", nextId++);
        this.title = title;
        this.status = status;
    }

    public enum Status {
        AVAILABLE,
        BORROWED,
        LOST
    }
}
