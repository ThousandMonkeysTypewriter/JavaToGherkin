package com.dsl.formats.redirect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import com.dsl.executor.Executor;
import com.dsl.executor.info.Element;
import com.dsl.executor.info.ExecData;
import com.google.gson.Gson;

public class RedirectDSL {

  HashMap<Integer, String> qoh = new HashMap<Integer, String>();
  ArrayList<ExecData> data = new ArrayList<ExecData>();
  Executor exec = new Executor();

  public RedirectDSL(ArrayList<String> inputs, ArrayList<Integer> outputs,
      Map<String, Collection<RedirectData>> redirects) {
    int count = 0;
    for (String q : inputs) {
      qoh.put(count, q);
      count ++;
    }

    for (Entry<Integer, String> q : qoh.entrySet()) {
      ExecData d = new ExecData();
      
      d.toEnvironment("value", q.getValue(), false);
      d.toEnvironment("redirects", redirects, false);
      d.toEnvironment("url_api", "http://yenisei.detectum.com:1919/search_default?region_id=1&q=", false);
//    d.toEnvironment("answer", outputs.get(q.getKey()), true);
      d.toEnvironment("answer", 1, true);

      d.toArgument("id", q.getKey());
      d.toArgument("query", q.getValue());
      
      d.toProgram("id", Executor.BEGIN);
      d.toProgram("program", "begin");
      
      data.add(d); 
    }
  }

  public void check_redirects() throws Exception {
    for (ExecData d : data) {
      //System.err.println("%%%");
    //  exec.query_api(d);
    //  exec.parse_response_type(d);
      exec.is_redirect(d);
      exec.validate(d);
      d.next_step();
      d.clear();
      //System.err.println(d.flush_buffer());
    }
  }
}
