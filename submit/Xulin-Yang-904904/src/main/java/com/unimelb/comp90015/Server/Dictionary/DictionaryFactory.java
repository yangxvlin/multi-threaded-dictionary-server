package com.unimelb.comp90015.Server.Dictionary;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-4-12 22:11:36
 * description: the factory to create dictionary instance
 **/

public class DictionaryFactory {
    /**
     * the singleton instance
     */
    private static DictionaryFactory ourInstance = new DictionaryFactory();

    /**
     * @return the singleton factory instance
     */
    public static DictionaryFactory getInstance() {
        return ourInstance;
    }

    private DictionaryFactory() {
    }

    /**
     * @param dictionaryFilePath dictionary file's path
     * @return the created SimpleDictionary instance
     */
    public SimpleDictionary createSimpleDictionary(String dictionaryFilePath) {
        return new SimpleDictionary(dictionaryFilePath);
    }
}
