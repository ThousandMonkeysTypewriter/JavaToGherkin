package com.dsl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Utils {

  public static String readFile(String filename) throws IOException{
    FileReader files = null;
    String txt = "";
    try {
      files = new FileReader(filename);
      txt += new Scanner(files).nextLine();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    finally {
      if (files!=null) 
        files.close(); 
    }
    
    return txt;
  }
}
