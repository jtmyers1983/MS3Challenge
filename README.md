    The purpose of this repo is to store the java application for the MS3 Interview Coding Challenge.
The challenge was to create a Java application, that consumes a .csv file, parses it, and store valid records in 
a sqlite database.  I chose to use a Maven project, utilizing the OpenCSV, Sqlite JDBC, and SLF4j / Logback dependencies.
In order to optimize the process, I chose to retrieve the data from the csv file line by line, and either store that line in a batch,
or write that file to the ms3Interview-bad.csv file if it is incomplete.  The batch will execute after 100 rows are stored,
minimizing the number of execute statements needed.  

Steps to run the application:
    Download the Maven project, and run the main() inside the App class. 
    You will also need a sqlite database created, with a table of 10 rows.
    I have this stored in the sqlite directory of the project with the file ms3Interview.db
    
Assumptions:
     - I assume a database already exists, with the appropriate table already created (since the instructions implied to create this manually),
       as the application will not create a new table or database automatically.
       
     - I assumed the files ms3Interview.log, and ms3Interview-bad.csv should not be updated when pushing to github, 
       so I have added those to the .gitignore file. 
       
     - For simplicity I chose to name my sqlite table "customers" and to use varchar(255) for the data types.  Obviously
       float or int types could be used for decimals and integers, and a BLOB datatype could be used for image data, however I
       did not think this was the focus of the challenge, and chose to use varchar(255) for all datatypes A - J of the table.
    