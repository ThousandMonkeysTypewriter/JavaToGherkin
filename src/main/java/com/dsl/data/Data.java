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
import com.dsl.formats.detection_log.EventAtMinute;
import com.dsl.formats.detection_log.LogDSL;
import com.dsl.formats.detection_log.LogUtils;
import com.dsl.formats.redirect.RedirectDSL;
import com.dsl.formats.redirect.RedirectData;
import com.dsl.formats.redirect.RedirectUtils;
import com.dsl.formats.redirect.RedirectsJson;
import com.unsupervised.Predictor;
import com.google.gson.Gson;

public class Data {

  public static final int limit = 15000;
  private static String dsl_buffer = "/root/data/dsl_buffer/";
  
  public static void main(String[] args) {
    int count = 0;
    Predictor pred = new Predictor();
    try {
      if (args[0].equals("generate_redirects")) {
        Map<String,Collection<RedirectData>> redirects = new HashMap<String, Collection<RedirectData>>();
        HashSet<String> queries = new HashSet<String>();      

        redirects = RedirectUtils.loadRedirects(
            new Gson().fromJson(new FileReader(dsl_buffer+"redirects.json"), RedirectsJson[].class));
        for ( ArrayList<String> r : new Gson().fromJson(new FileReader(dsl_buffer+"all_requests.json"), ArrayList[].class)) {
          if (count > limit)
            break;
          queries.add(r.get(0).trim().toLowerCase());
          count ++;
        }

        Set<String> qs = new HashSet<String>(queries);
        ArrayList<String> inputs = new ArrayList<String>(qs);

        Integer[] outputs = new Gson().fromJson(new FileReader("/root/data/dsl_buffer/answers.json"), Integer[].class);
    
        RedirectDSL rdsl = new RedirectDSL(inputs, new ArrayList<Integer>(Arrays.asList(outputs)), redirects);
        rdsl.check_redirects();
      } else if (args[0].equals("log")) {
        String client = args[2];
        int client_id =  Integer.parseInt(args[3]);
        String command = args[1];
        
        ArrayList<Event> events = new ArrayList<Event>();
        BufferedReader br = new BufferedReader(new FileReader("/root/NeuralProgramSynthesis/dsl/data/logs/"+client+".json"));
        for(String line; (line = br.readLine()) != null; ) {
          Event e = new Gson().fromJson(line.replace("@timestamp", "timestamp"), Event.class);
          if (e._source != null) {
            if (count > limit)
                break;
            events.add(e);
            e.setTimes();
            e.setId(count);
          }
          count++;
        }
        br.close();
        
        HashMap<EventAtMinute, EventAtMinute> inputs = new HashMap<>();
        
        for (Event e : events) {
          EventAtMinute ev = new EventAtMinute(e);
          if (!inputs.containsKey(ev))
            inputs.put(ev, ev);
          else
            inputs.get(ev).update(ev);
        }

        ArrayList<Integer> outputs = pred.execute(events);
                
        LogDSL log_dsl = new LogDSL(new ArrayList<>(inputs.values()), outputs, client, command, client_id);
        log_dsl.execute(command);
      } else if (args[0].equals("test")) {
        HashSet<String> inps = new HashSet<String>();      

        for ( ArrayList<String> r : new Gson().fromJson(new FileReader(dsl_buffer+"all_requests.json"), ArrayList[].class)) {
          if (count > limit)
            break;
          inps.add(r.get(0).trim().toLowerCase());
          count ++;
        }

        Set<String> ins = new HashSet<String>(inps);
        ArrayList<String> inputs = new ArrayList<String>(ins);

        Integer[] outputs = new Gson().fromJson(new FileReader(dsl_buffer+"answers.json"), Integer[].class);
    
        RedirectDSL rdsl = new RedirectDSL(inputs, new ArrayList<Integer>(Arrays.asList(outputs)), null);
        rdsl.check_redirects();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}