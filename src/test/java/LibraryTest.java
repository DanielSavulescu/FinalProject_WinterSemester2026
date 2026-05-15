import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.domain.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    // testing borrowItems method
    @Test
    @DisplayName("Student can borrow a book successfully")
    public void borrowItemsTest1() {
        Library library = new Library();
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();

        Student student = new Student("Alice");
        Book book = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.add(book);

        library.borrowItems(book, student);

        assertEquals(Item.Status.BORROWED, book.getStatus());
    }

    @Test
    @DisplayName("Student cannot borrow a DVD")
    public void borrowItemsTest2() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Student student = new Student("Alice");
        Dvd dvd = new Dvd("Inception", Item.Status.AVAILABLE, "Christopher Nolan", 148);
        Library.items.add(dvd);

        assertThrows(IllegalArgumentException.class, () -> library.borrowItems(dvd, student));
    }

    @Test
    @DisplayName("Student cannot borrow a Magazine")
    public void borrowItemsTest3() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Student student = new Student("Alice");
        Magazine magazine = new Magazine("Time", Item.Status.AVAILABLE, 42, "Time USA");
        Library.items.add(magazine);

        assertThrows(IllegalArgumentException.class, () -> library.borrowItems(magazine, student));
    }

    @Test
    @DisplayName("Student cannot exceed 5-book borrow limit")
    public void borrowItemsTest4() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Student student = new Student("Alice");

        for (int i = 0; i < 5; i++) {
            Book book = new Book("Book " + i, Item.Status.AVAILABLE, "978000000000" + i, "Author", "Genre");
            Library.items.add(book);
            library.borrowItems(book, student);
        }

        Book extraBook = new Book("Extra", Item.Status.AVAILABLE, "9780000000009", "Author", "Genre");
        Library.items.add(extraBook);

        assertThrows(IllegalArgumentException.class, () -> library.borrowItems(extraBook, student));
    }

    @Test
    @DisplayName("Teacher can borrow a DVD successfully")
    public void borrowItemsTest5() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Teacher teacher = new Teacher("Bob");
        Dvd dvd = new Dvd("Inception", Item.Status.AVAILABLE, "Christopher Nolan", 148);
        Library.items.add(dvd);

        library.borrowItems(dvd, teacher);

        assertEquals(Item.Status.BORROWED, dvd.getStatus());
    }

    @Test
    @DisplayName("Teacher can borrow a Magazine successfully")
    public void borrowItemsTest6() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Teacher teacher = new Teacher("Bob");
        Magazine magazine = new Magazine("Time", Item.Status.AVAILABLE, 42, "Time USA");
        Library.items.add(magazine);

        library.borrowItems(magazine, teacher);

        assertEquals(Item.Status.BORROWED, magazine.getStatus());
    }

    @Test
    @DisplayName("Teacher cannot exceed 10-item borrow limit")
    public void borrowItemsTest7() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Teacher teacher = new Teacher("Bob");

        for (int i = 0; i < 10; i++) {
            Book b = new Book("Book " + i, Item.Status.AVAILABLE, "978000000000" + i, "Author", "Genre");
            Library.items.add(b);
            library.borrowItems(b, teacher);
        }

        Book extraBook = new Book("Extra", Item.Status.AVAILABLE, "9780000000009", "Author", "Genre");
        Library.items.add(extraBook);

        assertThrows(IllegalArgumentException.class, () -> library.borrowItems(extraBook, teacher));
    }

    @Test
    @DisplayName("Cannot borrow an item not in the library")
    public void borrowItemsTest8() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Student student = new Student("Alice");
        Book outsideBook = new Book("Unknown", Item.Status.AVAILABLE, "9780000000001", "Nobody", "None");

        assertThrows(IllegalArgumentException.class, () -> library.borrowItems(outsideBook, student));
    }

    //testing returnItems method
    @Test
    @DisplayName("Returning an item changes status to AVAILABLE")
    public void returnItemsTest1() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Student student = new Student("Alice");
        Book book = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.add(book);

        library.borrowItems(book, student);
        library.returnItems(book, student);

        assertEquals(Item.Status.AVAILABLE, book.getStatus());
    }

    @Test
    @DisplayName("Returned item is removed from user's borrowed list")
    public void returnItemsTest2() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Student student = new Student("Alice");
        Book book = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.add(book);

        library.borrowItems(book, student);
        library.returnItems(book, student);

        assertFalse(student.getUserBorrowedItems().contains(book));
    }

    @Test
    @DisplayName("Returned item is added back to the library")
    public void returnItemsTest3() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Student student = new Student("Alice");
        Book book = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.add(book);

        library.borrowItems(book, student);
        library.returnItems(book, student);

        assertTrue(Library.items.contains(book));
    }

    //testing the searchStream method
    @Test
    @DisplayName("searchStream finds item by title (case-insensitive)")
    public void searchStreamTest1() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Book book = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.add(book);

        List<Item> results = library.searchStream("harry");

        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("searchStream finds book by author")
    public void searchStreamTest2() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Book book = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.add(book);

        List<Item> results = library.searchStream("rowling");

        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("searchStream deduplicates copies with the same title")
    public void searchStreamTest3() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Book copy1 = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Book copy2 = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.addAll(List.of(copy1, copy2));

        List<Item> results = library.searchStream("harry");

        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("searchStream returns empty list when no match")
    public void searchStreamTest4() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Book book = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.add(book);

        List<Item> results = library.searchStream("zzznomatch");

        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("searchStream is case-insensitive")
    public void searchStreamTest5() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Book book = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.add(book);

        List<Item> upper = library.searchStream("HARRY");
        List<Item> lower = library.searchStream("harry");

        assertEquals(upper.size(), lower.size());
    }

    //testing the searchRecursive method
    @Test
    @DisplayName("searchRecursive finds item by title")
    public void searchRecursiveTest1() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Dvd dvd = new Dvd("Inception", Item.Status.AVAILABLE, "Christopher Nolan", 148);
        Library.items.add(dvd);

        List<Item> results = library.searchRecursive("inception", 0, new ArrayList<>(), new HashSet<>());

        assertEquals(1, results.size());
        assertEquals("Inception", results.get(0).getTitle());
    }

    @Test
    @DisplayName("searchRecursive finds book by author")
    public void searchRecursiveTest2() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Book book = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.add(book);

        List<Item> results = library.searchRecursive("rowling", 0, new ArrayList<>(), new HashSet<>());

        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("searchRecursive deduplicates copies with the same title")
    public void searchRecursiveTest3() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Book copy1 = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Book copy2 = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.addAll(List.of(copy1, copy2));

        List<Item> results = library.searchRecursive("harry", 0, new ArrayList<>(), new HashSet<>());

        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("searchRecursive returns empty list when no match")
    public void searchRecursiveTest4() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Book book = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.add(book);

        List<Item> results = library.searchRecursive("zzznomatch", 0, new ArrayList<>(), new HashSet<>());

        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("searchRecursive is case-insensitive")
    public void searchRecursiveTest5() {
        Library.items = new ArrayList<>();
        Library.users = new ArrayList<>();
        Library library = new Library();

        Book book = new Book("Harry Potter", Item.Status.AVAILABLE, "9780747532743", "J.K. Rowling", "Fantasy");
        Library.items.add(book);

        List<Item> upper = library.searchRecursive("HARRY", 0, new ArrayList<>(), new HashSet<>());
        List<Item> lower = library.searchRecursive("harry", 0, new ArrayList<>(), new HashSet<>());

        assertEquals(upper.size(), lower.size());
    }
}
