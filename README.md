**Problem Statement**

This project is a Spring Boot-based REST API for managing an inventory of products. The system supports CRUD operations, filtering, sorting, pagination, and email notifications when stock levels fall below a defined threshold.

**Features**

- *CRUD Operations*: Create, Read, Update, and Delete products in the inventory.
- *Filtering*: Filter products based on categories, price ranges, or availability.
- *Sorting*: Sort products by name, price, or stock quantity.
- *Pagination*: Efficiently handle large datasets with pagination support.
- *Email Notifications*: Send notifications via email when stock levels of any product fall below a specified threshold, using Thymeleaf templates for email formatting.
- *MySQL Integration*: Use Spring JDBC Template to perform database operations with a MySQL database.
- *Error Handling*: Proper HTTP status codes and error handling for all API requests.
