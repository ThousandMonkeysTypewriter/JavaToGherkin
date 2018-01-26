package com.dsl.executor.info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import akka.remote.artery.TaskRunner.Add;

import com.dsl.data.formats.redirect.RedirectData;
import com.google.gson.Gson;

public class Recources {

  public HashMap<String, Object> buffer = new HashMap<String, Object>();

  public Recources() {
  }
  
  public void put(String k, Object v) {
    buffer.put(k, v);
  }
  
  public Object get(String k) {
    return buffer.get(k);
  }
}
