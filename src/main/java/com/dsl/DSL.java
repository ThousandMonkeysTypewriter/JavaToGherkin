package com.dsl;

import java.io.IOException;
import com.dsl.data.DataLoader;

public class DSL {

  DataLoader data;

  public static void main(String[] args) {
    try {
      Utils.readFile("/root/NeuralProgramSynthesis/dsl/data/redirects.json");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
  }
}