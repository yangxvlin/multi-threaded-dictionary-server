package com.unimelb.comp90015.Client.ConnectionStrategy;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-27 15:33
 * description:
 **/

public interface IConnectionStrategy {
    String searchConnection(String word);

    String addConnection(String word, String meaning);

    String deleteConnection(String word);
}
