package com.unimelb.comp90015.Server;

import org.json.simple.JSONArray;
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

public class Dictionary {
    public Dictionary(String dictionaryFilePath) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(dictionaryFilePath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject employeeList = (JSONObject) obj;
            System.out.println(employeeList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
