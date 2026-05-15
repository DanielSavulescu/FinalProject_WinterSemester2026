package org.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.library.util.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Library {
    public static List<Item> items;
    public static List<User> users;

    /**
     * permits the user to borrow items
     *
     * @param item the item to be borrowed
     * @param user the user borrowing the item
     */
    public static void borrowItems(Item item, User user) throws IllegalArgumentException {
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
    public static void returnItems(Item item, User user) {
        user.userBorrowedItems.remove(item);
        items.add(item);
        item.setStatus(Item.Status.AVAILABLE);
    }

    /**
     * enables the user to search for an item using a key word using a stream
     *
     * @param keyWord the key word to search for the item
     * @return the results of the search
     */
    public static List<Item> searchStream(String keyWord) {
        String lowerCaseKey = keyWord.toLowerCase();
        Set<String> seenTitles = new HashSet<>();

        return items.stream()
                .filter(item -> {
                    boolean matchesKeyword = item.getTitle().toLowerCase().contains(lowerCaseKey);
                    if (!matchesKeyword && item instanceof Book) {
                        matchesKeyword = ((Book) item).getAuthor().toLowerCase().contains(lowerCaseKey);
                    }
                    return matchesKeyword && seenTitles.add(item.getTitle().toLowerCase());
                })
                .toList();
    }

    /**
     * enables the user to search for an item using a key word using recursion
     *
     * @param keyword    the keyword to search for the item
     * @param index      the index to check
     * @param results    the results of the search so far
     * @param seenTitles the titles that have already been checked
     * @return the list of all the items containing that key word
     */
    public static List<Item> searchRecursive(String keyword, int index, List<Item> results, Set<String> seenTitles) {
        if (index == items.size()) {
            return results;
        }

        Item currentItem = items.get(index);
        String lowerKeyword = keyword.toLowerCase();
        String title = currentItem.getTitle().toLowerCase();

        boolean matches = title.contains(lowerKeyword);
        if (!matches && currentItem instanceof Book) {
            matches = ((Book) currentItem).getAuthor().toLowerCase().contains(lowerKeyword);
        }

        if (matches && !seenTitles.contains(title)) {
            results.add(currentItem);
            seenTitles.add(title);
        }

        return searchRecursive(keyword, index + 1, results, seenTitles);
    }

    /**
     * initializes the users from a user file
     *
     * @param userPath the path for the user file
     */
    public static void initializeUsers(String userPath) {
        File file = new File(userPath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] elements = line.split(",");
                /*
                structure for a line should be like so: Occupation,Name
                example -> Student, Name
                 */
                String name = elements[1];
                if (elements[0].equalsIgnoreCase("student")) {
                    Student student = new Student(name);
                    users.add(student);
                } else if (elements[0].equalsIgnoreCase("teacher")) {
                    Teacher teacher = new Teacher(name);
                    users.add(teacher);
                } else if (elements[0].equalsIgnoreCase("admin")) {
                    Admin admin = new Admin(name);
                    users.add(admin);
                } else {
                    throw new RuntimeException("User indeterminable");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * initializes the items from a user file
     *
     * @param itemsPath the path for the item file
     */
    public static void initializeItems(String itemsPath) {
        File file = new File(itemsPath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] elements = line.split(",");

                /*
                structure for a line should be like so:
                Book,title,status,isbn,author,genre
                Dvd,title,status,director,duration
                Magazine,title,status,issueNumber,publisher
                example -> Student, Name
                 */
                String title = elements[1].toLowerCase();
                Item.Status status;

                if (elements[2].equalsIgnoreCase("borrowed")) {
                    status = Item.Status.BORROWED;
                } else if (elements[2].equalsIgnoreCase("available")) {
                    status = Item.Status.AVAILABLE;
                } else {
                    status = Item.Status.LOST;
                }

                switch (elements[0]) {
                    case "book" -> {
                        Book book = new Book(title, status, elements[3], elements[4], elements[5]);
                        items.add(book);
                    }

                    case "dvd" -> {
                        Dvd dvd = new Dvd(title, status, elements[3], Integer.parseInt(elements[4]));
                        items.add(dvd);
                    }

                    case "magazine" -> {
                        Magazine magazine = new Magazine(title, status, Integer.parseInt(elements[3]), elements[4]);
                        items.add(magazine);
                    }

                    default -> throw new RuntimeException("Item indeterminable");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * loads users into a csv file
     */
    public static void loadUsers() {
        File file = new File(Constants.USERS_CSV_PATH);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            for (User user : users) {
                if (user instanceof Student) {
                    fileWriter.write("Student,");
                } else if (user instanceof Teacher) {
                    fileWriter.write("Teacher,");
                } else {
                    fileWriter.write("Admin,");
                }
                fileWriter.write(user.getId() + ",");
                fileWriter.write(user.getName() + ",");
                fileWriter.write("Borrowed Items:");
                for (Item item : user.getUserBorrowedItems()) {
                    fileWriter.write(item.getId() + "-" + item.getTitle());
                }
                fileWriter.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * loads books into a csv file
     */
    public static void loadBooks() {
        File file = new File(Constants.BOOKS_CSV_PATH);

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            for (Item item : items) {
                if (item instanceof Book) {
                    fileWriter.write(item.getId() + ",");
                    fileWriter.write(item.getTitle() + ",");
                    fileWriter.write(((Book) item).getIsbn() + ",");
                    fileWriter.write(((Book) item).getAuthor() + ",");
                    fileWriter.write(((Book) item).getGenre() + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
