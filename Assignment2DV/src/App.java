import java.sql.*;
import java.util.Scanner;

public class App {

    static final String DB_URL = "jdbc:sqlite:C:/Users/Usuario/Desktop/ADS&AI/B3/Intro To Soft.Ing/ASSIGNMENT2-BOOCKTRACKER DATABASE/BOOKHAB_DATABASE.db";

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 10) {
            System.out.println("\n BOOKTRACKER MENU:");
            System.out.println("1. Add a user");
            System.out.println("2. Get reading habits of a user");
            System.out.println("3. Change the title of a book");
            System.out.println("4. Delete a record from ReadingHabit");
            System.out.println("5. Mean age of users");
            System.out.println("6. Number of users that read a specific book");
            System.out.println("7. Total pages read by all users");
            System.out.println("8. Users that have read more than one book");
            System.out.println("9. Add 'Name' column to User table");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> addUser(scanner);
                case 2 -> getReadingHabits(scanner);
                case 3 -> changeBookTitle(scanner);
                case 4 -> deleteReadingHabit(scanner);
                case 5 -> getMeanAge();
                case 6 -> getUsersPerBook(scanner);
                case 7 -> getTotalPagesRead();
                case 8 -> getUsersWithMoreThanOneBook();
                case 9 -> addNameColumn();
                case 10 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

 // 1. Add a user
    static void addUser(Scanner scanner) {
        System.out.print("UserID: ");
        int userId = scanner.nextInt();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Gender (m/f): ");
        String gender = scanner.nextLine();

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO \"reading_habits_dataset(ages)\" (userID, age, gender) VALUES (?, ?, ?)")) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.executeUpdate();
            System.out.println("User added!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 2. Get reading habits of a user
    static void getReadingHabits(Scanner scanner) {
        System.out.print("UserID: ");
        int userId = scanner.nextInt();

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM reading_habits_dataset WHERE userID = ?")) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getInt("habitID") + " | " +
                    rs.getString("book") + " | " +
                    rs.getInt("pagesRead") + " | " +
                    rs.getString("submissionMoment")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 3. Change the title of a book
    static void changeBookTitle(Scanner scanner) {
        System.out.print("Current title: ");
        String oldTitle = scanner.nextLine();
        System.out.print("New title: ");
        String newTitle = scanner.nextLine();

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                "UPDATE reading_habits_dataset SET book = ? WHERE book = ?")) {

            pstmt.setString(1, newTitle);
            pstmt.setString(2, oldTitle);
            System.out.println(pstmt.executeUpdate() + " record updated.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 4. Delete a record from ReadingHabit
    static void deleteReadingHabit(Scanner scanner) {
        System.out.print("HabitID to delete: ");
        int habitId = scanner.nextInt();

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                "DELETE FROM reading_habits_dataset WHERE habitID = ?")) {

            pstmt.setInt(1, habitId);
            System.out.println(pstmt.executeUpdate() + " record deleted.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 5. Mean age of users
    static void getMeanAge() {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                "SELECT AVG(age) AS meanAge FROM \"reading_habits_dataset(ages)\"")) {

            if (rs.next())
                System.out.printf("Mean age: %.2f years%n", rs.getDouble("meanAge"));

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 6. Number of users that read a specific book
    static void getUsersPerBook(Scanner scanner) {
        System.out.print("Book title: ");
        String book = scanner.nextLine();

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                "SELECT COUNT(DISTINCT userID) AS total FROM reading_habits_dataset WHERE book = ?")) {

            pstmt.setString(1, book);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                System.out.println("Users that read this book: " + rs.getInt("total"));

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 7. Total pages read by all users
    static void getTotalPagesRead() {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                "SELECT SUM(pagesRead) AS total FROM reading_habits_dataset")) {

            if (rs.next())
                System.out.println("Total pages read: " + rs.getInt("total"));

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 8. Users that have read more than one book
    static void getUsersWithMoreThanOneBook() {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                "SELECT COUNT(*) AS total FROM (SELECT userID FROM reading_habits_dataset GROUP BY userID HAVING COUNT(DISTINCT book) > 1)")) {

            if (rs.next())
                System.out.println("Users with more than one book: " + rs.getInt("total"));

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 9. Add 'Name' column to User table
    static void addNameColumn() {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate("ALTER TABLE \"reading_habits_dataset(ages)\" ADD COLUMN Name TEXT");
            System.out.println("Column 'Name' added!");

        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate column"))
                System.out.println("Column 'Name' already exists.");
            else
                System.out.println("Error: " + e.getMessage());
        }
    }
}
