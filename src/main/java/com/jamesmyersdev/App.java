package com.jamesmyersdev;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, CsvValidationException {

        CSVReader reader = new CSVReader(new FileReader("ms3Interview.csv"));

        String [] nextLine;
        nextLine = reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            if (Arrays.asList(nextLine).contains("")) {
                System.out.println(nextLine[0]);
            }
        }

    }
}
