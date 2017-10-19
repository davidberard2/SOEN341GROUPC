package com.projectfirebase.soen341.root;

public class Helper{
    public static boolean isNullOrEmpty(String s){ return s == null || s.trim() == ""; }
    public static boolean isNullOrEmpty(Object[] o) { return o == null || o.length < 1; }
}
