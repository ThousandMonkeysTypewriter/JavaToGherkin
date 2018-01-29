package com.dsl.executor.info;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Step {
  public HashMap<String, Element> environment = new HashMap<String, Element>();
  public HashMap<String, Element> program = new HashMap<String, Element>();
  public HashMap<String, Element> argument = new HashMap<String, Element>();
  
  public void fill_defaults(Step st) {
    copy_from(environment, st.environment);
    copy_from(program, st.program);
    copy_from(argument, st.argument);
  }

  private void copy_from(HashMap<String, Element> map1,
      HashMap<String, Element> map2) {
    for (Entry<String, Element> e : map2.entrySet()) 
      map1.put(e.getKey(), e.getValue());
  } 

  public String toString () {
    return "environment "+environment+", program"+program+", argument"+argument;
  }

  public void clear() {
    for(Iterator<Entry<String, Element>> it = environment.entrySet().iterator(); it.hasNext(); ) {
      Entry<String, Element> entry = it.next();
      if(!entry.getValue().isVisible()) {
        it.remove();
      }
    }
    
    for(Iterator<Entry<String, Element>> it = program.entrySet().iterator(); it.hasNext(); ) {
      Entry<String, Element> entry = it.next();
      if(!entry.getValue().isVisible()) {
        it.remove();
      }
    }
    
    for(Iterator<Entry<String, Element>> it = argument.entrySet().iterator(); it.hasNext(); ) {
      Entry<String, Element> entry = it.next();
      if(!entry.getValue().isVisible()) {
        it.remove();
      }
    }
  }
}
