package com.dsl.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.dsl.formats.detection_log.Event;
import com.dsl.formats.detection_log.LogDSL;
import com.dsl.formats.detection_log.LogUtils;
import com.dsl.formats.redirect.RedirectDSL;
import com.dsl.formats.redirect.RedirectData;
import com.dsl.formats.redirect.RedirectUtils;
import com.dsl.formats.redirect.RedirectsJson;
import com.google.gson.Gson;

public class Data {

  public static final int limit = 100000;

  public static void main(String[] args) {
    int count = 0;
    try {
      if (args[0].equals("generate_redirects")) {
        Map<String,Collection<RedirectData>> redirects = new HashMap<String, Collection<RedirectData>>();
        HashSet<String> queries = new HashSet<String>();      

        redirects = RedirectUtils.loadRedirects(
            new Gson().fromJson(new FileReader("/root/NeuralProgramSynthesis/dsl/data/redirects.json"), RedirectsJson[].class));
        for ( ArrayList<String> r : new Gson().fromJson(new FileReader("/root/NeuralProgramSynthesis/dsl/data/all_requests.json"), ArrayList[].class)) {
          if (count > limit)
            break;
          queries.add(r.get(0).trim().toLowerCase());
          count ++;
        }
        Integer[] outputs = new Gson().fromJson(new FileReader("/root/NeuralProgramSynthesis/dsl/data/answers.json"), Integer[].class);

        Set<String> qs = new HashSet<String>(queries);
        ArrayList<String> inputs = new ArrayList<String>(qs);

        RedirectDSL rdsl = new RedirectDSL(inputs, new ArrayList<Integer>(Arrays.asList(outputs)), redirects);
        rdsl.check_redirects();
      } else if (args[0].equals("ad_log_query")) {
        ArrayList<Event> inputs = new ArrayList<Event>();
        
        BufferedReader br = new BufferedReader(new FileReader("/root/NeuralProgramSynthesis/dsl/data/logs/"+args[1]+".json"));
        for(String line; (line = br.readLine()) != null; ) {
          Event e = new Gson().fromJson(line.replace("@timestamp", "timestamp"), Event.class);
          if (e._source != null) {
            inputs.add(e);
            e.setTimes();
          }
        }
        br.close();
        
        ArrayList<Integer> outputs = null;

        LogDSL log_dsl = new LogDSL(inputs, outputs);
        log_dsl.detect_anomaity_query_logs();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}