package com.dsl.executor;

import com.dsl.data.Utils;
import com.dsl.executor.info.InnerInfo;
import com.google.gson.Gson;

public class Executor {

  Gson gson = new Gson();
  
  public void query_api(InnerInfo d) throws Exception {
    String json = Utils.readUrl((String)d.getRecources().get("url_api"));
    d.getRecources().put("api_response", json);
    
    d.getTrace().trace_add("Call_Api");
  }

  public void get_response_type(InnerInfo d) {
    String s = (String)d.getRecources().get("api_response");
    
    s = s.substring(s.indexOf("\"type\":\"") + 1);
    s = s.substring(0, s.indexOf("\""));
    
    d.getRecources().put("api_response_type", s);
  }

}
