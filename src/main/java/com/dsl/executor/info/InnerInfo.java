package com.dsl.executor.info;

public class InnerInfo {
  Argument arg;
  Environment env;
  Recources data;
  Trace trc;

  public InnerInfo(Argument a, Environment e, Recources d, Trace t) {
    // TODO Auto-generated constructor stub
  }
  
  public Argument getArgument() {
    return arg;
  }
  
  public Recources getRecources() {
    return data;
  }
  
  public Environment getEnvironment() {
    return env;
  }
  
  public Trace getTrace() {
    return trc;
  }
}
