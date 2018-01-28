package com.dsl.executor;

import com.dsl.data.Utils;
import com.dsl.executor.info.ExecData;
import com.google.gson.Gson;
import java.net.URLEncoder;

public class Executor {

  //programs
  public static final int BEGIN     = 0;
  private static final int CALL_API = 1;
  private static final int PARSE    = 2;
  private static final int CHECK    = 3;
  private static final int ALARM    = 4;
  private static final int NO_ALARM = 5;
  
  //traces
  public static final int ENVIRONMENT = 1;
  public static final int PROGRAM     = 2;
  public static final int ARGUMENT   = 3;
  
  Gson gson = new Gson();
  
  int alarms = 0;
  int no_alarms = 0;
  
  public void query_api(ExecData d) throws Exception {
    d.next_step();
    String url = (String)d.fromEnvironment("url_api").getValue()+URLEncoder.encode((String)d.fromArgument("query").getValue(), "UTF-8");
    String json = Utils.readUrl(url);
    System.err.println(url+" - "+json.length());

    d.toEnvironment("api_response", json, false);
    d.toEnvironment("web", 1, true);
    
    d.toProgram("id", CALL_API);
    d.toProgram("program", "call_api");
  }

  public void parse_response_type(ExecData d) {
    d.next_step();
    String s = (String)d.fromEnvironment("api_response").getValue();
    
    s = s.substring(s.indexOf("\"type\":\"") + 8);
    s = s.substring(0, s.indexOf("\""));
    
    d.toEnvironment("api_response_type", s, false);
    d.toEnvironment("parse", 1, true);
    
    d.toProgram("id", PARSE);
    d.toProgram("program", "parse");
  }

  public void is_redirect(ExecData d) {
    d.next_step();
    
    //if (((String)d.fromEnvironment("api_response_type").getValue()).equals("redirect"))
    if (((String)d.fromArgument("query").getValue()).contains("а"))
      d.toEnvironment("is_redirect", 1, true);
    else
      d.toEnvironment("is_redirect", 0, true);
    
    d.toProgram("id", CHECK);
    d.toProgram("program", "check");
  }

  public void validate(ExecData d) {
    d.next_step();
    
    if (((Integer)d.fromEnvironment("is_redirect").getValue()) != ((Integer)d.fromEnvironment("answer").getValue())) {
      d.toProgram("id", ALARM);
      d.toProgram("program", "alarm");
      alarms += 1;
    }
    else {
      d.toProgram("id", NO_ALARM);
      d.toProgram("program", "no_alarm");
      no_alarms += 1;
    }
    
    System.err.println("alarm: "+alarms+", no_alarm: "+no_alarms);
  }
}
