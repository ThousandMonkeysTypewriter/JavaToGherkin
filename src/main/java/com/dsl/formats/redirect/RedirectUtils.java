package com.dsl.formats.redirect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedirectUtils {
  
  public static Map<String, Collection<RedirectData>> loadRedirects(
      RedirectsJson[] fromJson) throws Exception {
    Pattern unglue = Pattern.compile("([^\\p{Space}-][\\p{IsAlphabetic},.;'\\[\\]<>:\"{}]+|[0-9]+([.,][0-9]+)*)");
    
    Map<String,Collection<RedirectData>> redirs = new HashMap<String,Collection<RedirectData>>();
    int count = 0;
    
    for ( RedirectsJson t : fromJson)
    {
      ArrayList<String> words = new ArrayList<String>();
      count++;
      
      Matcher mt = unglue.matcher(t.request);
      while ( mt.find() ) {
        String w = mt.group(1);
        
        words.add(w.toLowerCase());
      }
      
      RedirectData r = new RedirectData(words, t.url, t.type, count);
      
      for ( String w : words) {
        Collection<RedirectData> coll = redirs.get(w);

        if (coll == null) {
          coll = new ArrayList<RedirectData>();
          redirs.put(w, coll);
        }

        coll.add(r);
      }
    }
    
    return redirs;
  }

}
