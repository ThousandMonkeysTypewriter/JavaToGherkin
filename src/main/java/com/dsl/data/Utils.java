package com.dsl.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
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
  
  public static void writeFile(List<String> lines, String filename) throws IOException {
    Path file = Paths.get(filename);
    Files.write(file, lines, Charset.forName("UTF-8"));
  }

  public static String readUrl(String urlString) throws Exception {
    BufferedReader reader = null;
    try {
      URL url = new URL(urlString);
      reader = new BufferedReader(new InputStreamReader(url.openStream()));
      StringBuffer buffer = new StringBuffer();
      int read;
      char[] chars = new char[1024];
      while ((read = reader.read(chars)) != -1)
        buffer.append(chars, 0, read); 

      return buffer.toString();
    } finally {
      if (reader != null)
        reader.close();
    }
  }
}
