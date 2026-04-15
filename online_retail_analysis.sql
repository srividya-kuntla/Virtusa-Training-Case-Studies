Table Creation:
CREATE TABLE Customers (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100),
    city VARCHAR(50)
);

CREATE TABLE Products (
    product_id INT PRIMARY KEY,
    name VARCHAR(120),
    category VARCHAR(50),
    price DECIMAL(10,2)
);

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    order_date DATE,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

CREATE TABLE Order_Items (
    order_id INT,
    product_id INT,
    quantity INT,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

1.Top-Selling Products:
SELECT 
    p.product_id,
    p.name,
    SUM(oi.quantity) AS total_units_sold
FROM Order_Items oi
JOIN Products p 
    ON oi.product_id = p.product_id
GROUP BY p.product_id, p.name
ORDER BY total_units_sold DESC;

2.MOST VALUABLE CUSTOMERS:
SELECT 
    c.customer_id,
    c.name,
    SUM(oi.quantity * p.price) AS total_spent
FROM Customers c
JOIN Orders o 
    ON c.customer_id = o.customer_id
JOIN Order_Items oi 
    ON o.order_id = oi.order_id
JOIN Products p 
    ON oi.product_id = p.product_id
GROUP BY c.customer_id, c.name
ORDER BY total_spent DESC;

3.MONTHLY REVENUE CALCULATION:
SELECT 
    DATE_FORMAT(o.order_date, '%Y-%m') AS sales_month,
    SUM(oi.quantity * p.price) AS monthly_revenue
FROM Orders o
JOIN Order_Items oi 
    ON o.order_id = oi.order_id
JOIN Products p 
    ON oi.product_id = p.product_id
GROUP BY sales_month
ORDER BY sales_month;

4.CATEGORY-WISE SALES ANALYSIS:
SELECT 
    p.category,
    SUM(oi.quantity) AS total_items_sold,
    SUM(oi.quantity * p.price) AS category_revenue
FROM Products p
JOIN Order_Items oi 
    ON p.product_id = oi.product_id
GROUP BY p.category
ORDER BY category_revenue DESC;

5.INACTIVE CUSTOMERS:
SELECT 
    c.customer_id,
    c.name
FROM Customers c
LEFT JOIN Orders o 
    ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.name
HAVING MAX(o.order_date) IS NULL 
   OR MAX(o.order_date) < DATE_SUB(CURRENT_DATE, INTERVAL 1 YEAR);
