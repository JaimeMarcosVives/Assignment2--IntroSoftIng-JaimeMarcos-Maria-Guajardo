# BookTracker – Reading Habits Database

A simple console-based Java application to manage reading habits using a SQLite database. Built for Assignment 2 of Intro to Software Engineering.

---

## What it does

You run it from the terminal and get a menu with 10 options to interact with the database:

1. Add a user
2. Get reading habits of a user
3. Change the title of a book
4. Delete a record from ReadingHabit
5. Mean age of users
6. Number of users that read a specific book
7. Total pages read by all users
8. Users that have read more than one book
9. Add 'Name' column to User table
10. Exit

---

## Requirements

- Java 8 or higher
- SQLite JDBC driver (`sqlite-jdbc-3.45.1.0.jar`) — already included

---

## How to run it

1. Make sure the database file path in `App.java` matches where you have `BOOKHAB_DATABASE.db` on your machine:
   ```java
   static final String DB_URL = "jdbc:sqlite:YOUR/PATH/HERE/BOOKHAB_DATABASE.db";
   ```

2. Compile:
   ```bash
   javac -cp "sqlite-jdbc-3.45.1.0.jar" App.java
   ```

3. Run:
   ```bash
   java -cp ".;sqlite-jdbc-3.45.1.0.jar" App
   ```




## Manual

When you run the app, this menu appears:

```
 BOOKTRACKER MENU:
1. Add a user
2. Get reading habits of a user
3. Change the title of a book
4. Delete a record from ReadingHabit
5. Mean age of users
6. Number of users that read a specific book
7. Total pages read by all users
8. Users that have read more than one book
9. Add 'Name' column to User table
10. Exit
Choose an option:
```



**Option 1 – Add a user**

Asks for a UserID, age and gender, then inserts the record into the database.
```
Choose an option: 1
UserID: 101
Age: 22
Gender (m/f): f
User added!
```



**Option 2 – Get reading habits of a user**

Asks for a UserID and prints all their reading records (habitID, book, pages read, date).
```
Choose an option: 2
UserID: 101
3 | The Hobbit | 150 | 2024-03-01
7 | Dune | 200 | 2024-03-15
```



**Option 3 – Change the title of a book**

Asks for the current title and the new one, and updates every record that matches.
```
Choose an option: 3
Current title: The Hobbit
New title: The Hobbit (Tolkien)
1 record updated.
```


**Option 4 – Delete a record**

Asks for a habitID and deletes that specific reading record.
```
Choose an option: 4
HabitID to delete: 3
1 record deleted.
```


**Option 5 – Mean age of users**

No input needed. Calculates and prints the average age of all users in the database.
```
Choose an option: 5
Mean age: 24.30 years
```



**Option 6 – Number of users that read a specific book**

Asks for a book title and counts how many distinct users have read it.
```
Choose an option: 6
Book title: Dune
Users that read this book: 5
```



**Option 7 – Total pages read**

No input needed. Sums up all pages read across all users and records.
```
Choose an option: 7
Total pages read: 18450
```


**Option 8 – Users with more than one book**

No input needed. Counts how many users have reading records for more than one distinct book.
```
Choose an option: 8
Users with more than one book: 12
```

---

**Option 9 – Add 'Name' column**

No input needed. Adds a `Name` column to the users table. If it already exists, it just tells you and moves on.
```
Choose an option: 9
Column 'Name' added!
```
or if already added:
```
Column 'Name' already exists.
```

---

**Option 10 – Exit**

Closes the app.
```
Choose an option: 10
Goodbye!
```

---

## Database structure

**`reading_habits_dataset`**
| Column | Type |
|---|---|
| habitID | INTEGER (PK) |
| userID | INTEGER |
| book | TEXT |
| pagesRead | INTEGER |
| submissionMoment | TEXT |

**`reading_habits_dataset(ages)`**
| Column | Type |
|---|---|
| userID | INTEGER (PK) |
| age | INTEGER |
| gender | TEXT |
| Name | TEXT (optional) |

---

## Common errors

- **DB not found** → check the path in `DB_URL`
- **ClassNotFoundException** → make sure the `.jar` is in the classpath
- **"duplicate column"** → option 9 already ran before, no worries, it handles it safely