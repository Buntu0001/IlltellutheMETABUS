package com.buntu.illtelluthemetabus;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.Bukkit;

public class IssueHandler {
  public static void errorSend(String pl, String fu, HashMap<String, String> va, String st) {
    try {
      Method method = Bukkit.getPluginManager().getPlugin("IssueTracker").getClass().getMethod("SendError",
          String.class, String.class, HashMap.class, String.class);
      method.invoke(Bukkit.getPluginManager().getPlugin("IssueTracker").getClass(), pl, fu, va, st);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
