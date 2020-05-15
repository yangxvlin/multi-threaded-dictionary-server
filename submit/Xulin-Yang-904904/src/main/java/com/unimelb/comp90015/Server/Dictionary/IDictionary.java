package com.unimelb.comp90015.Server.Dictionary;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-24 0:15
 * description: interface for dictionary to provide extendability
 **/

public interface IDictionary {

    /**
     * @param word the word to be searched
     * @return word's meaning
     * @throws WordNotFoundException the word not existing in the dictionary
     */
    String search(String word) throws WordNotFoundException;

    /**
     * @param word word the word to be deleted
     * @throws WordNotFoundException the word not existing in the dictionary
     */
    void remove(String word) throws WordNotFoundException;

    /**
     * @param word the word to be added
     * @param meaning the word's meaning
     * @throws DuplicateWordException the word already existing in the dictionary
     */
    void add(String word, String meaning) throws DuplicateWordException;

    /**
     * save the dictionary to disk
     */
    void save();
}
