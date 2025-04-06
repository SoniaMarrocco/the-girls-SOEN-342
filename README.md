[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/-9QgYBSe)
# SOEN 342 

### Contributors:
- **Sonia Marrocco**  
  Student ID: 40250575

- **Somiya Amroso Colatosti**  
  Student ID: 40190025

## Iteration2:
  Design Pattern: Singletone implemented in Administrator class

# Java Project
This is a Java project developed using **IntelliJ IDEA**. The project is not set up with **Maven** or **Gradle**, so dependencies must be added manually.

## 1. Setting Up the SQLite JDBC Dependency
   - The SQLite JDBC driver `.jar` file is already located in the `src/lib` directory of the project.
     
   - Go  to **File > Project Structure**.
   - In the **Modules** section, click on your project and navigate to the **Dependencies** tab.
   - Click the `+` button, then choose **JARs or directories**.
   - Locate and select the `sqlitejdbc-3.49.1.0.jar`  and click **OK**.

## 2. Running the Project
   - Compile the project using `javac`:
     ```bash
     javac -cp "src/libs/sqlite-jdbc-3.49.1.0.jar" -d out src/**/*.java 
     ```
     - Run the project using `java`:
     ```bash
     java -cp "out:src/libs/sqlite-jdbc-3.49.1.0.jar" Main 
     ```





javac -cp .:libs/sqlite-jdbc-3.49.1.0.jar Database/SQLiteDBCreator.java
java -cp .:libs/sqlite-jdbc-3.49.1.0.jar Database.SQLiteDBCreator

Database/auction_system.db 
