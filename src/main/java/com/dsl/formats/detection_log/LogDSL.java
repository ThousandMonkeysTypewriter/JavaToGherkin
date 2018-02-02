package com.dsl.formats.detection_log;

import java.util.ArrayList;
import java.util.HashMap;
import com.dsl.DSL;
import com.dsl.executor.Executor;
import com.dsl.executor.info.ExecData;
import com.dsl.executor.info.Step;

public class LogDSL extends DSL {

  HashMap<Integer, String> qoh = new HashMap<Integer, String>();
  ArrayList<ExecData> data = new ArrayList<ExecData>();
  Executor exec = new Executor();

  public LogDSL(ArrayList<Event> inputs, ArrayList<Integer> outputs) {
    super(inputs, outputs);
    for (Event e : inputs) {
      ExecData d = new ExecData();
      
      d.toEnvironment("answer", check(e), true);
      d.toEnvironment("period", 5, true);
      d.toEnvironment("output", 0, true);
      d.toEnvironment("terminate", false, true);

      d.toArgument("id", LogUtils.countThisMinute(e));
      
      d.toProgram("id", Executor.BEGIN);
      d.toProgram("program", "begin");
      
      data.add(d); 
    }
  }

  private int check(Event e) {
    return 0;
  }

  public void detect_anomaity_query_logs() {
    ArrayList<HashMap<Integer, Step>> out = new ArrayList<HashMap<Integer, Step>>();
    for (ExecData d : data) {
      exec.load_period(d);
      exec.validate(d);

      d.flush_buffer(out);
      d.clear();
    }
  }
}

