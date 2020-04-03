package com.unimelb.comp90015.Server.Dictionary;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

import static com.unimelb.comp90015.Util.Constant.*;
import static com.unimelb.comp90015.Util.Util.popupErrorDialog;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-24 0:15
 * description: the simple instance of dictionary with the ability to search, add, delete, save
 **/

public class SimpleDictionary implements IDictionary {
    /**
     * key, value pair for dictionary
     */
    private JSONObject dictionary;

    /**
     * dictionary file path on disk
     */
    private String dictionaryFilePath;

    public SimpleDictionary(String dictionaryFilePath) {
        this.dictionaryFilePath = dictionaryFilePath;

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(dictionaryFilePath)) {
            //Read JSON file
            dictionary = (JSONObject) jsonParser.parse(reader);

        } catch (ParseException e) {
            popupErrorDialog(ERROR_INVALID_DICTIONARY_FORMAT_CODE, ERROR_INVALID_DICTIONARY_FORMAT_CONTENT);
        } catch (IOException e) {
            popupErrorDialog(ERROR_INVALID_DICTIONARY_PATH_CODE, ERROR_INVALID_DICTIONARY_PATH_CONTENT);
        }
    }

    /**
     * @param word the word to be searched
     * @return meaning of the word
     * @throws WordNotFoundException the word not existing in the dictionary
     */
    @Override
    public synchronized String search(String word) throws WordNotFoundException {
        if (dictionary.containsKey(word)) {
            return dictionary.get(word).toString();
        } else {
            throw new WordNotFoundException();
        }
    }

    /**
     * @param word word the word to be deleted
     * @throws WordNotFoundException the word not existing in the dictionary
     */
    @Override
    public synchronized void remove(String word) throws WordNotFoundException {
        if (dictionary.containsKey(word)) {
            dictionary.remove(word);
            save();
        } else {
            throw new WordNotFoundException();
        }
    }

    /**
     * @param word    the word to be added
     * @param meaning the word's meaning
     * @throws DuplicateWordException the word already existing in the dictionary
     */
    @Override
    public synchronized void add(String word, String meaning) throws DuplicateWordException {
        if (dictionary.containsKey(word)) {
            throw new DuplicateWordException();
        } else {
            dictionary.put(word, meaning);
            save();
        }
    }

    /**
     * save the dictionary to disk
     */
    @Override
    public synchronized void save() {
        File f= new File(dictionaryFilePath);
        try {
            Writer out = new FileWriter(f);
            System.out.println(dictionary.toJSONString());
            out.write(dictionary.toJSONString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
