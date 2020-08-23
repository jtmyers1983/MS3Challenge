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
    public static void main( String[] args ) throws IOException, CsvValidationException {

        // initialize reader and writer
        CSVReader reader = new CSVReader(new FileReader("ms3Interview.csv"));
        CSVWriter writer = new CSVWriter(new FileWriter("ms3Interview-bad.csv"));

        // connect to database
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:sqlite/ms3Interview.db");
            System.out.println("Connection Successful");
            stmt = conn.prepareStatement("INSERT INTO customers(A, B, C, D, E, F, G, H, I, J) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        String [] nextLine;
        nextLine = reader.readNext(); // skipping row with headers

        while ((nextLine = reader.readNext()) != null) {
            if (Arrays.asList(nextLine).contains("")) { // OpenCSV will replace null values with empty string
                writer.writeNext(nextLine); // if empty string exists as value, write that line to ms3Interview-bad.csv
            } else {
                addRow(stmt, nextLine);
            }
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        writer.close();
        reader.close();
    }

    public static void addRow(PreparedStatement stmt, String[] nextLine) {
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

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
