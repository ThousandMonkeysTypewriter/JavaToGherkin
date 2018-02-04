package com.dsl.executor;

import com.dsl.data.Utils;
import com.dsl.executor.info.ExecData;
import com.google.gson.Gson;

import java.net.InetAddress;
import java.net.URLEncoder;

import com.dsl.formats.detection_log.Event;
import com.dsl.formats.detection_log.LogUtils;

import java.util.ArrayList;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.common.settings.Settings;

public class Executor {

  //programs
  public static final int BEGIN     = 0;
  private static final int CALL_API = 1;
  private static final int PARSE    = 2;
  private static final int CHECK    = 3;
  private static final int ALARM    = 4;
  private static final int NO_ALARM = 5;
  
  private static final int LOAD_LOG = 6;
  
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

    inner_check(((String)d.fromArgument("query").getValue()).contains("а"), d);
  }
  
  public void compare_to_period(ExecData d) {
    int period = (Integer)d.fromEnvironment("period").getValue();
    
    double now = LogUtils.countAtMinute((Event)d.fromArgument("event").getValue(), (ArrayList<Event>)d.fromEnvironment("log").getValue(), 0);
    double ago = LogUtils.countAtMinute((Event)d.fromArgument("event").getValue(), (ArrayList<Event>)d.fromEnvironment("log").getValue(), period);
    
    double min;
    double max;
    
    if (now > ago) {
      min = ago;
      max = now;
    } else {
      min = now;
      max = ago;      
    }
    
    double percent_diff = (max - min) / (min / 100);
    
    //    System.err.println(min+":"+max+"^"+percent_diff);
    
    inner_check(percent_diff < 30 || (now%2 == 0), d);
  }

  public void validate(ExecData d) {
    d.next_step();
    
    if (((Integer)d.fromEnvironment("output").getValue()) != ((Integer)d.fromEnvironment("answer").getValue())) {
      d.toProgram("id", ALARM);
      d.toProgram("program", "alarm");
      alarms += 1;
    }
    else {
      d.toProgram("id", NO_ALARM);
      d.toProgram("program", "no_alarm");
      no_alarms += 1;
    }
    
    d.toEnvironment("terminate", true, true);
    
    System.err.println("alarm: "+alarms+", no_alarm: "+no_alarms);
  }
  
  /**
  *###############################################################
  */
  
  private void inner_check(boolean condition, ExecData d) {
    if (((Integer)d.fromEnvironment("output").getValue()) == 0) {
      if (condition)
        d.toEnvironment("output", 2, true);
      else
        d.toEnvironment("output", 1, true);
    }
    
    d.toProgram("id", CHECK);
    d.toProgram("program", "check");
  }
}
