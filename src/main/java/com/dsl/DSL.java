package com.dsl;

import java.util.ArrayList;

import com.dsl.executor.Executor;
import com.dsl.executor.info.ExecData;

abstract public class DSL {
  
  public Executor exec;
  public ArrayList<ExecData> data;
  
  public DSL (ArrayList inputs, ArrayList outputs) {
    exec = new Executor();
    data = new ArrayList<ExecData>();
  }
}
