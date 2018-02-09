package com.dsl.executor.subprocess;

import com.dsl.executor.Executor;
import com.dsl.executor.info.ExecData;

public class SubProcess {
  public static void inner_check(boolean condition, ExecData d) {
    if (((Integer)d.fromEnvironment("output").getValue()) == 0) {
      if (condition)
        d.toEnvironment("output", 2, true);
      else
        d.toEnvironment("output", 1, true);
    }
    
    d.toProgram("id", Executor.CHECK);
    d.toProgram("program", "check");
  }
}
