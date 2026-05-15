package org.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;

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

    public static class ItemComparator implements Comparator<Item> {
        @Override
        public int compare(Item o1, Item o2) {
            return o1.getTitle().compareToIgnoreCase(o2.title) == 0 ? o1.getId().compareTo(o2.getId()) : o1.getTitle().compareToIgnoreCase(o2.title);
        }
    }
}
