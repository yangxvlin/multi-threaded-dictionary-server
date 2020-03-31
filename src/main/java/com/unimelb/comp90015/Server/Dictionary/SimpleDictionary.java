package com.unimelb.comp90015.Server.Dictionary;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-24 0:15
 * description:
 **/

public class SimpleDictionary implements IDictionary {
    private JSONObject dictionary;

    private String dictionaryFilePath;

    public SimpleDictionary(String dictionaryFilePath) {
        this.dictionaryFilePath = dictionaryFilePath;

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(dictionaryFilePath)) {
            //Read JSON file
            dictionary = (JSONObject) jsonParser.parse(reader);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized String search(String word) throws WordNotFoundException {
        if (dictionary.containsKey(word)) {
            return dictionary.get(word).toString();
        } else {
            throw new WordNotFoundException();
        }
    }

    @Override
    public synchronized void remove(String word) throws WordNotFoundException {
        if (dictionary.containsKey(word)) {
            dictionary.remove(word);
            save();
        } else {
            throw new WordNotFoundException();
        }
    }

    @Override
    public synchronized void add(String word, String meaning) throws DuplicateWordException {
        if (dictionary.containsKey(word)) {
            throw new DuplicateWordException();
        } else {
            dictionary.put(word, meaning);
            save();
        }
    }

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
