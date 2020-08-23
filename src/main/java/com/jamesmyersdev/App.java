package com.jamesmyersdev;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, CsvValidationException {

        // initialize reader and writer
        CSVReader reader = new CSVReader(new FileReader("ms3Interview.csv"));
        CSVWriter writer = new CSVWriter(new FileWriter("ms3Interview-bad.csv"));

        String [] nextLine;
        nextLine = reader.readNext(); // skipping row with headers

        while ((nextLine = reader.readNext()) != null) {
            if (Arrays.asList(nextLine).contains("")) { // OpenCSV will replace null values with empty string
                writer.writeNext(nextLine); // if empty string exists as value, write that line to ms3Interview-bad.csv
            } else {

            }
        }

        writer.close();
        reader.close();
    }
}
