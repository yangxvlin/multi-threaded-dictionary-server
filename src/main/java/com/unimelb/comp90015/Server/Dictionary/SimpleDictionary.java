package com.unimelb.comp90015.Server.Dictionary;

import com.unimelb.comp90015.Constant;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-24 0:15
 * description:
 **/

public class SimpleDictionary implements IDictionary {
    private JSONObject dictionary;

    public SimpleDictionary(String dictionaryFilePath) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(dictionaryFilePath))
        {
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
    public synchronized String remove(String word) {
        return null;
    }

    @Override
    public synchronized String add(String word) {
        return null;
    }
}
