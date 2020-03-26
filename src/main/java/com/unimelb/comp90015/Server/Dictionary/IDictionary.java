package com.unimelb.comp90015.Server.Dictionary;

public interface IDictionary {
    String search(String word) throws WordNotFoundException;

    String remove(String word);

    String add(String word);
}
