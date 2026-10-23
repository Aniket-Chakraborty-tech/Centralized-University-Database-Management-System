# Centralized University DBMS

Java + MySQL (JDBC) project. Handles students, faculty, courses, enrollment, and a research cell.

## Setup
1. Install MySQL 8 and create the DB:
   ```
   mysql -u root -p < sql/schema.sql
   mysql -u root -p < sql/sample_data.sql
   ```
2. Download MySQL Connector/J and put the jar in a `lib/` folder.
3. Set credentials (optional, defaults to root / empty password):
   ```
   export UNI_DB_USER=root
   export UNI_DB_PASS=yourpassword
   ```
4. Compile and run (Java 17+):
   ```
   mkdir out
   javac -cp "lib/*" -d out $(find src -name "*.java")
   java -cp "out:lib/*" Main        # on Windows use ; instead of :
   ```

Docs are in `docs/SRS.md`.
