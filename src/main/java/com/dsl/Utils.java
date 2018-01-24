package com.dsl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Utils {

  public static void readFile(String filename) throws IOException{
    FileReader files = null;
    try {
      files = new FileReader(filename);
      String line = new Scanner(files).nextLine();
      System.err.println(line);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    finally {
      if (files!=null) 
        files.close(); 
    }
  }
}
