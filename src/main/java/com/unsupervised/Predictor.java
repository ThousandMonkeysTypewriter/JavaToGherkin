package com.unsupervised;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.dsl.formats.detection_log.Event;
import com.dsl.formats.detection_log.EventAtMinute;
import com.dsl.formats.detection_log.LogDSL;
import com.dsl.formats.detection_log.LogUtils;
import com.dsl.formats.redirect.RedirectDSL;
import com.dsl.formats.redirect.RedirectData;
import com.dsl.formats.redirect.RedirectUtils;
import com.dsl.formats.redirect.RedirectsJson;
import com.google.gson.Gson;

public class Predictor {
     public Predictor() {
     }
     
     public ArrayList<Integer> execute() {
         ArrayList<Integer> predictions = new ArrayList<>();
         return predictions;
     }
}