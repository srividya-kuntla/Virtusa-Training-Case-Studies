--1. Table Creation
CREATE TABLE Students (
    StudentID INT PRIMARY KEY,
    Name VARCHAR(100),
    JoinDate DATE
);

CREATE TABLE Books (
    BookID INT PRIMARY KEY,
    Title VARCHAR(150),
    Category VARCHAR(50)
);

CREATE TABLE IssuedBooks (
    IssueID INT PRIMARY KEY,
    StudentID INT,
    BookID INT,
    IssueDate DATE,
    ReturnDate DATE,
    FOREIGN KEY (StudentID) REFERENCES Students(StudentID),
    FOREIGN KEY (BookID) REFERENCES Books(BookID)
);

--2. Overdue Books Query
SELECT s.StudentID, s.Name, b.Title, ib.IssueDate
FROM IssuedBooks ib
JOIN Students s ON ib.StudentID = s.StudentID
JOIN Books b ON ib.BookID = b.BookID
WHERE ib.ReturnDate IS NULL
AND ib.IssueDate < CURDATE() - INTERVAL 14 DAY;

--3. Popularity Index
SELECT b.Category, COUNT(*) AS BorrowCount
FROM IssuedBooks ib
JOIN Books b ON ib.BookID = b.BookID
GROUP BY b.Category
ORDER BY BorrowCount DESC;

--4. Data Cleanup
DELETE FROM Students
WHERE StudentID NOT IN (
    SELECT DISTINCT StudentID FROM IssuedBooks
)
AND JoinDate < CURDATE() - INTERVAL 3 YEAR;