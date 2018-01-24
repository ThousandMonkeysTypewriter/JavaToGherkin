package com.dsl;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.dsl.data.DataLoader;
import com.dsl.data.formats.redirect.RedirectData;
import com.dsl.data.formats.redirect.RedirectsJson;
import com.google.gson.Gson;

public class DSL {

  DataLoader data;

  public static void main(String[] args) {
    Map<String,Collection<RedirectData>> redirects;

    try {
      redirects = loadRedirects(
          new Gson().fromJson(new FileReader("/root/NeuralProgramSynthesis/dsl/data/redirects.json"), RedirectsJson[].class));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
  }
}