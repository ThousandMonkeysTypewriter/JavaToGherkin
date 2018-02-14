package com.dsl.formats.detection_log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogUtils {

  public static double countAtMinute(Event e, ArrayList<Event> ents, int ago, String c, double sla) {
    double count = 0.0;
    double green_zone = sla - ((sla/100) * 10);
    for (Event ent : ents) {
        if (e.year==ent.year && e.month==ent.month && e.day==ent.day && e.hour==ent.hour && ent.minute==(e.minute-ago)) {
          if (c.equals("query"))
            count += 1;
          else if (c.equals("sla")) 
        }
    }
    return count;
  }
}
