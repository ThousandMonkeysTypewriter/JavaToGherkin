package com.dsl;

import java.util.ArrayList;

import com.dsl.executor.Executor;
import com.dsl.executor.info.ExecData;
import java.util.HashMap;

import com.dsl.DSL;
import com.dsl.data.Utils;
import com.dsl.executor.Executor;
import com.dsl.executor.info.ExecData;
import com.dsl.executor.info.Step;
import com.google.gson.Gson;

abstract public class DSL {
  
  public Executor exec;
  public ArrayList<ExecData> data;
  public ArrayList<HashMap<Integer, Step>> out = new ArrayList<HashMap<Integer, Step>>();

  
  public DSL (ArrayList inputs, ArrayList outputs) {
    exec = new Executor();
    data = new ArrayList<ExecData>();
  }
  
  public void send() throws Exception {
    String strout = new Gson().toJson(out);
    ArrayList<String> tmp = new ArrayList<String>();
    tmp.add(strout);
    
    Utils.writeFile(tmp, "/root/NeuralProgramSynthesis/dsl/data/data_buffer.json");
  }
}
