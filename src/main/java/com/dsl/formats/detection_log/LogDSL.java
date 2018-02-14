package com.dsl.formats.detection_log;

import java.util.ArrayList;
import java.util.HashMap;

import com.dsl.DSL;
import com.dsl.executor.Executor;
import com.dsl.executor.info.ExecData;
import com.dsl.executor.info.Step;
import com.dsl.formats.detection_log.LogUtils;

public class LogDSL extends DSL {

  HashMap<Integer, String> qoh = new HashMap<Integer, String>();
  ArrayList<ExecData> data = new ArrayList<ExecData>();
  Executor exec = new Executor();
  
  private static final int QUERY  = 1;
  private static final int SLA    = 1;
  private static final int STATUS = 1;


  public LogDSL(ArrayList<Event> inputs, ArrayList<Integer> outputs, String client, String command, int client_id) {
    super(inputs, outputs);
    for (Event e : inputs) {
      ExecData d = new ExecData();

      d.toEnvironment("answer", 2, true);
      d.toEnvironment("date1", 0, true);
      d.toEnvironment("date2", 0, true);
      d.toEnvironment("date1_diff", 0, true);
      d.toEnvironment("date2_diff", 0, true);
      d.toEnvironment("output", 0, true);
      d.toEnvironment("terminate", false, true);
      d.toEnvironment("client_id", client_id, true);
      d.toEnvironment("client", client, false);
      d.toEnvironment("log", inputs, false);
    
      d.toProgram("id", Executor.BEGIN);
      d.toProgram("program", "begin");
      
      d.toArgument("id", (int)LogUtils.countAtMinute(e, inputs, 0, command));
      d.toArgument("event", e);
    
      if (command.equals("query")) 
        d.toEnvironment("anomaly_type", QUERY, false);
      else if (command.equals("status")) 
        d.toEnvironment("anomaly_type", STATUS, false);
      else if (command.equals("sla")) 
        d.toEnvironment("anomaly_type", SLA, false);
      
      data.add(d); 
    }
  }

  public void execute(String command)throws Exception {
    for (ExecData d : data) {
      
      if (command.equals("query")) {
        exec.compare_to_period(d);
        exec.compare_to_period(d);
      } else if (command.equals("status")) {
        exec.check_status(d);
      } else if (command.equals("sla")) {
        exec.check_time(d);
      }
      
      exec.prepare_data(d);
      exec.anomaly_detect(d);
      exec.validate(d);

      d.flush_buffer(out);
      d.clear();
    }
    send();
  }
}

