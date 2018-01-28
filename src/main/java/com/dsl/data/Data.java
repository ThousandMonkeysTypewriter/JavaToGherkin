package com.dsl.data;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dsl.formats.redirect.RedirectDSL;
import com.dsl.formats.redirect.RedirectData;
import com.dsl.formats.redirect.RedirectUtils;
import com.dsl.formats.redirect.RedirectsJson;
import com.google.gson.Gson;

public class Data {

  public static void main(String[] args) {
    if (args[0].equals("generate_redirects")) {
      Map<String,Collection<RedirectData>> redirects = new HashMap<String, Collection<RedirectData>>();
      HashSet<String> queries = new HashSet<String>();
      
      try {
        redirects = RedirectUtils.loadRedirects(
            new Gson().fromJson(new FileReader("/root/NeuralProgramSynthesis/dsl/data/redirects.json"), RedirectsJson[].class));
        int count = 0;
        for ( ArrayList<String> r : new Gson().fromJson(new FileReader("/root/NeuralProgramSynthesis/dsl/data/all_requests.json"), ArrayList[].class)) {
          if (count > 100000)
              break;
          queries.add(r.get(0).trim().toLowerCase());
          count ++;
        }
        Integer[] outputs = new Gson().fromJson(new FileReader("/root/NeuralProgramSynthesis/dsl/data/answers.json"), Integer[].class);
        
        Set<String> qs = new HashSet<String>(queries);
        ArrayList<String> inputs = new ArrayList<String>(qs);
        
        RedirectDSL rdsl = new RedirectDSL(inputs, new ArrayList<Integer>(Arrays.asList(outputs)), redirects);
        rdsl.check_redirects();
        // rdsl.save_data();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } 
    }
  }
}