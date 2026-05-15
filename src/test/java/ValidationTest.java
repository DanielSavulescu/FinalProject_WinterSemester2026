import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.domain.Book;
import org.library.domain.Item;
import org.library.service.Validation;

public class ValidationTest {
    @Test
    @DisplayName("Valid 13-digit ISBN returns true")
    public void validationTest1() {
        Book book = new Book("Clean Code", Item.Status.AVAILABLE, "9780132350884", "Robert Martin", "Tech");
        boolean expected = true;
        boolean actual = Validation.validation(book);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("ISBN with less than 13 digits returns false")
    public void validationTest2() {
        Book book = new Book("Clean Code", Item.Status.AVAILABLE, "978013235", "Robert Martin", "Tech");
        boolean expected = false;
        boolean actual = Validation.validation(book);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("ISBN with more than 13 digits returns false")
    public void validationTest3() {
        Book book = new Book("Clean Code", Item.Status.AVAILABLE, "97801323508841234", "Robert Martin", "Tech");
        boolean expected = false;
        boolean actual = Validation.validation(book);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("ISBN with letters returns false")
    public void validationTest4() {
        Book book = new Book("Clean Code", Item.Status.AVAILABLE, "978013235088A", "Robert Martin", "Tech");
        boolean expected = false;
        boolean actual = Validation.validation(book);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("ISBN with special characters returns false")
    public void validationTest5() {
        Book book = new Book("Clean Code", Item.Status.AVAILABLE, "978-013235088", "Robert Martin", "Tech");
        boolean expected = false;
        boolean actual = Validation.validation(book);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("ISBN with spaces returns false")
    public void validationTest6() {
        Book book = new Book("Clean Code", Item.Status.AVAILABLE, "978 013235088", "Robert Martin", "Tech");
        boolean expected = false;
        boolean actual = Validation.validation(book);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Empty ISBN returns false")
    public void validationTest7() {
        Book book = new Book("Clean Code", Item.Status.AVAILABLE, "", "Robert Martin", "Tech");
        boolean expected = false;
        boolean actual = Validation.validation(book);
    }
}
