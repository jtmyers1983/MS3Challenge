package com.jamesmyersdev;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class App 
{
    public static void main( String[] args ) {

        // ---- connect to database ----
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:sqlite/ms3Interview.db");
            System.out.println("Connection Successful");
            stmt = conn.prepareStatement("INSERT INTO customers(A, B, C, D, E, F, G, H, I, J) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // ------------------------------

        // ---- csvParser method used to read the csv file and perform appropriate action ----
        try {
            csvParser("ms3Interview.csv", "ms3Interview-bad.csv", stmt);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (CsvValidationException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // ------------------------------

        // ---- close connection to database ----
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // ------------------------------
    }

    public static void addRow(PreparedStatement stmt, String[] nextLine) {
        // add row data to current batch to be inserted into database
        try {
            stmt.setString(1, nextLine[0]);
            stmt.setString(2, nextLine[1]);
            stmt.setString(3, nextLine[2]);
            stmt.setString(4, nextLine[3]);
            stmt.setString(5, nextLine[4]);
            stmt.setString(6, nextLine[5]);
            stmt.setString(7, nextLine[6]);
            stmt.setString(8, nextLine[7]);
            stmt.setString(9, nextLine[8]);
            stmt.setString(10, nextLine[9]);

            stmt.addBatch(); // add current row nextLine to batch
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void csvParser(String csvFile, String badCsvFile, PreparedStatement stmt) throws IOException, CsvValidationException, SQLException {

        CSVReader reader = new CSVReader(new FileReader(csvFile));
        CSVWriter writer = new CSVWriter(new FileWriter(badCsvFile));
        int batchSize = 0;
        String [] nextLine;
        nextLine = reader.readNext(); // skipping row with headers

        while ((nextLine = reader.readNext()) != null) {
            if (Arrays.asList(nextLine).contains("")) { // OpenCSV will replace null values with empty string
                writer.writeNext(nextLine); // if empty string exists as value, write that line to ms3Interview-bad.csv
            } else {
                addRow(stmt, nextLine);
            }
            if (batchSize++ > 100) { // execute every 100 rows
                stmt.executeBatch();
                batchSize = 0;
            }
        }

        if (batchSize > 0) { // execute remaining data
            stmt.executeBatch();
        }
        writer.close();  // close reader and writer
        reader.close();
    }
}
