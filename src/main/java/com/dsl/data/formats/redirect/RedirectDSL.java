package com.dsl.data.formats.redirect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import com.detectum.api.Result;
import com.detectum.api.ResultRedirect;
import com.detectum.config.SearchConfiguration;
import com.detectum.redirect.Redirect;
import com.detectum.redirect.RedirectData;
import com.detectum.redirect.RedirectToken;
import com.detectum.redirect.RedirectsJson;
import com.detectum.request.Request;
import com.detectum.request.Word;
import com.detectum.util.Ranking;
import com.dsl.executor.Executor;
import com.dsl.executor.info.Argument;
import com.dsl.executor.info.Recources;
import com.dsl.executor.info.Environment;
import com.dsl.executor.info.InnerInfo;
import com.dsl.executor.info.Trace;
import com.google.gson.Gson;

public class RedirectDSL {

  HashMap<Integer, String> qoh = new HashMap<Integer, String>();
  ArrayList<InnerInfo> data = new ArrayList<InnerInfo>();
  Executor exec = new Executor();

  public RedirectDSL(ArrayList<String> inputs, ArrayList<Integer> outputs,
      Map<String, Collection<RedirectData>> redirects) {
    int count = 0;
    for (String q : inputs) {
      qoh.put(count, q);
      count ++;
    }

    for (Entry<Integer, String> q : qoh.entrySet()) {
      Argument a = new Argument(q.getKey());
      Environment e = new Environment();

      Recources d = new Recources();
      d.put("value", q.getValue());
      d.put("redirects", redirects);
      d.put("url_api", "http://yenisei.detectum.com:1919/search_default?region_id=1&q=");
      
      Trace t = new Trace();

      data.add(new InnerInfo(a, e, d, t)); 
    }
  }

  public void save_data() {
    // TODO Auto-generated method stub

  }

  public void check_redirects() {
    for (InnerInfo d : data) {
      exec.query_api(d);
      exec.get_response_type(d);
      is_redirect();
      validate();
    }
  }

  private void validate() {
    // TODO Auto-generated method stub
    
  }

  private void is_redirect() {
   
  }

  private void is_in_list_contains(InnerInfo d) {
    // TODO Auto-generated method stub
    
  }
  
//  public void check() throws Exception
//  {
//    Map<String,Collection<RedirectData>> redirects = SearchConfiguration.loadRedirects(
//        new Gson().fromJson(json, RedirectsJson[].class));
//    
//    for (Request request : requestRedirectList())
//    {
//      Result res = Redirect.isRedirect(request, "", redirects);
//
//      org.junit.Assert.assertEquals(request.request(), true, checkResult(res));
//    }
//    
//    for (Request request : requestNonRedirectList())
//    {
//      Result res = Redirect.isRedirect(request, "", redirects);
//
//      org.junit.Assert.assertEquals(request.request(), false, checkResult(res));
//    }
//  }
}
