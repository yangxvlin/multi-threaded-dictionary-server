package com.unimelb.comp90015.Server.Dictionary;

public interface IDictionary {
    String search(String word) throws WordNotFoundException;

    void remove(String word) throws WordNotFoundException;

    void add(String word, String meaning) throws DuplicateWordException;

    void save();
}
