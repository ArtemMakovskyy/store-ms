-- 1. Find all customers with a Gmail address
SELECT *
FROM customers
WHERE email LIKE '%@gmail.com';

-- 2. Get customers registered before January 1st, 2023
SELECT *
FROM customers
WHERE registered_at < '2023-01-01';

-- 3. Get all orders with status 'SHIPPED'
SELECT *
FROM customer_orders
WHERE status = 'SHIPPED';

-- 4. Count the number of orders per customer
SELECT c.full_name, COUNT(o.id) AS orders_count
FROM customers c
         LEFT JOIN customer_orders o ON c.id = o.customer_id
GROUP BY c.id, c.full_name;

-- 5. Find customers with more than 5 orders
SELECT c.full_name, COUNT(o.id) AS orders_count
FROM customers c
         JOIN customer_orders o ON c.id = o.customer_id
GROUP BY c.id, c.full_name
HAVING COUNT(o.id) > 5;

-- 6. Get orders made in July 2024
SELECT *
FROM customer_orders
WHERE order_date BETWEEN '2024-07-01' AND '2024-07-31';

-- 7. Find customers who have no orders
SELECT *
FROM customers c
WHERE NOT EXISTS (
    SELECT 1
    FROM customer_orders o
    WHERE o.customer_id = c.id
);

-- 8. Count orders by each order status
SELECT status, COUNT(*) AS orders_count
FROM customer_orders
GROUP BY status;

-- 9. Get each customer’s most recent order
SELECT c.full_name, o.order_date
FROM customers c
         JOIN customer_orders o ON c.id = o.customer_id
WHERE o.order_date = (
    SELECT MAX(order_date)
    FROM customer_orders
    WHERE customer_id = c.id
);

-- 10. Top 10 customers by number of orders
SELECT c.full_name, COUNT(o.id) AS orders_count
FROM customers c
         JOIN customer_orders o ON c.id = o.customer_id
GROUP BY c.id, c.full_name
ORDER BY orders_count DESC
LIMIT 10;

-- 11. Find customers who have at least one canceled order
SELECT DISTINCT c.*
FROM customers c
         JOIN customer_orders o ON c.id = o.customer_id
WHERE o.status = 'CANCELED';

-- 12. Count orders per customer per status
SELECT c.full_name, o.status, COUNT(*) AS count_per_status
FROM customers c
         JOIN customer_orders o ON c.id = o.customer_id
GROUP BY c.full_name, o.status
ORDER BY c.full_name;

-- 13. Customers registered in 2024 who made at least one order
SELECT DISTINCT c.*
FROM customers c
         JOIN customer_orders o ON c.id = o.customer_id
WHERE YEAR(c.registered_at) = 2024;

-- 14. Customers who made orders in both July and August 2024
SELECT c.full_name
FROM customers c
WHERE EXISTS (
    SELECT 1
    FROM customer_orders o
    WHERE o.customer_id = c.id AND o.order_date BETWEEN '2024-07-01' AND '2024-07-31'
)
  AND EXISTS (
    SELECT 1
    FROM customer_orders o
    WHERE o.customer_id = c.id AND o.order_date BETWEEN '2024-08-01' AND '2024-08-31'
);

-- 15. Total and average number of orders per customer per month
SELECT c.full_name,
       COUNT(o.id) AS total_orders,
       ROUND(COUNT(o.id) / COUNT(DISTINCT CONCAT(YEAR(o.order_date), '-', MONTH(o.order_date))), 2) AS avg_per_month
FROM customers c
         JOIN customer_orders o ON c.id = o.customer_id
GROUP BY c.id, c.full_name
ORDER BY avg_per_month DESC;
