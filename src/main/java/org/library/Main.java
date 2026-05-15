package org.library;

import org.library.domain.*;

import java.util.ArrayList;

public class Main {
    static void main() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Book book1 = new Book("Java101", Item.Status.BORROWED, "1234567891023", "Steve", "Coding");
        Book book2 = new Book("Python101", Item.Status.AVAILABLE, "1234567893023", "Steve", "Coding");

        Library.items.add(book1);
        Library.items.add(book2);

        Student student1 = new Student("Bobby");
        Student student2 = new Student("Jon");
        Teacher teacher1 = new Teacher("Johnny");
        Teacher teacher2 = new Teacher("Emma");
        Admin admin = new Admin("Daniel");

        Library.users.add(student1);
        Library.users.add(student2);
        Library.users.add(teacher1);
        Library.users.add(teacher2);
        Library.users.add(admin);
        Library.borrowItems(book2, student1);
        Library.loadUsers();
        Library.loadBooks();
    }
}
