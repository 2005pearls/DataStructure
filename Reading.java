/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structurephase1;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author janaalmengash
 */
public class Reading {
    public static void Load(String fileName) {
        String line = null;
        try {
            File f = new File(fileName);
            Scanner sc = new Scanner(f, "UTF-8");

            if (sc.hasNextLine()) sc.nextLine();

            while (sc.hasNextLine()) {
                line = sc.nextLine();
                if (line == null) continue;

                line = line.trim();
                if (line.length() < 3) {
                    System.out.println("Empty line found, skipping this line = " + line);
                    continue;
                }

                System.out.println(line);
                String[] a = line.split(",", 2);
                if (a.length < 2) {
                    System.out.println("Bad line, skipping: " + line);
                    continue;
                }

                String idStr   = a[0].trim();
                String content = a[1].trim();

                int id = Integer.parseInt(idStr); 

                System.out.println("id = " + id + ", content = " + content);
            }

            sc.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Last line read = " + line);
        }
    }
    public static void main(String[] args) {
        Load("reviews.csv");
    }

}
