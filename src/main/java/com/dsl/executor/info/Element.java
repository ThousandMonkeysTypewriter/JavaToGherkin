package com.dsl.executor.info;

public class Element {

  private String key;
  private Object value;
  private int type;
  private boolean visible = true;
  
  public Element(String k, Object v, int t, boolean vis_) {
    key = k;
    value = v;
    type = t;
    visible = vis_;
  }
  
  public String getKey() {
    return key;
  }

  public Object getValue() {
    return value;
  }
  
  public int getType() {
    return type;
  }
}