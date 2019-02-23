/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2ee.webcrawler.utils;

import com.opencsv.CSVWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author An Le
 */
public class MyUtils {
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length); 
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    
    public static void exportCsvFile(List entries) throws IOException {
        try (FileOutputStream fos = new FileOutputStream("items.csv");
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                CSVWriter writer = new CSVWriter(osw)) {
            writer.writeAll(entries);
        }      
    }
}
