[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/-9QgYBSe)
# SOEN 342 

### Contributors:
- **Sonia Marrocco**  
  Student ID: 40250575

- **Somiya Amroso Colatosti**  
  Student ID: 40190025

  ### ‼️‼️ Combination of all FINAL diagrams are in the [`Iteration3`](/artifacts/Iteration3) folder.
  - this includes all iteration 1 and 2 work
 
**DEMO VIDEO**
https://drive.google.com/file/d/1MVqEJwTqQZaWQok14BWJvvO4DL-ilWp8/view?usp=sharing

# Iteration2:
  Design Pattern: Singletone implemented in Administrator class

In our system, the singleton design pattern with lazy initialization was used for creating the admin account for a session. This is because through the system session, only one admin is allowed. Hence if you look in the `AuctionSystem/Administrater.java` class...
- The constructor is private
- There is the private static AdminInstance
- the public static `getInstance()` is the only way to get the admin instance

 Architecture: Layered Architecture
- Presentation Layer: Main.java (User interaclion and terminal flow)
- Domain Logic Layer: AuctionSystem (Core application logic)
- Persistence Layer : DBAccess (`Table Data Gateway` --> Data access and manipulation)
- Infrastructure: Database (Squlite database and low level database setup and connection)

# Iteration3: 
Iteration 3 contains all of the combined system diagrams, system sequence diagrams, contracts... of all the other iterations combined. 

## Java Project
This is a Java project developed using **IntelliJ IDEA**. The project is not set up with **Maven** or **Gradle**.


## Running the Project
   - Navigate to `the-girls-SOEN-342` directory
   - Compile the project using `javac`:
     ```bash
     javac -cp "src/libs/sqlite-jdbc-3.49.1.0.jar" -d out src/**/*.java 
     ```
     - Run the project using `java`:
     ```bash
     java -cp "out:src/libs/sqlite-jdbc-3.49.1.0.jar" Main 
     ```

## Beginner Database
- The `/Database/auction_system.db` fiels provides an already populated database for you to use.
If database does not work, delete the file, navigate to src directory, and run these two prompts:
     ```bash
     javac -cp .:libs/sqlite-jdbc-3.49.1.0.jar Database/SQLiteDBCreator.java 
     ```
     ```bash
     java -cp .:libs/sqlite-jdbc-3.49.1.0.jar Database.SQLiteDBCreator 
     ```



