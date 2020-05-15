package com.unimelb.comp90015.Client.GUI;

import com.unimelb.comp90015.Client.ConnectionStrategy.IConnectionStrategy;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.TokenTypes;
import org.fife.ui.rsyntaxtextarea.spell.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.unimelb.comp90015.Util.Constant.APP_ICON_PATH;
import static com.unimelb.comp90015.Util.Util.loadFileFromJar;
import static java.awt.Component.CENTER_ALIGNMENT;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 15:25
 * description: the dictionary application GUI for the client to search, add,
 *              delete word to the dictionary in the server
 **/

public class ClientGUI {
    /**
     * frame object
     */
    private JFrame frame;

    /**
     * the dashboard to display response from server
     */
    private JTextArea dashboard = new JTextArea();

    /**
     * text box for user to input word
     */
//    private JTextField wordTextField;
    private RSyntaxTextArea wordTextField;

    private JButton btnSearch;

    /**
     * given application connection strategy to the server
     */
    private IConnectionStrategy connectionStrategy;

    public ClientGUI(String appName, IConnectionStrategy connectionStrategy) {
        this.connectionStrategy = connectionStrategy;

        // create JFrame instance
        frame = new JFrame(appName);
        // Setting the width and height of frame
        frame.setSize(1024, 720);
        // setup configuration
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(new EmptyBorder(7, 7, 7, 7));

        // setup the interactive component on screen
        placeIcons(panel);
        placeTextSearch(panel);
        placeAdd(panel);
        placeRemove(panel);

        // setup the dashboard
        dashboard.setEditable(false);
        dashboard.setBackground(SystemColor.window);
        dashboard.setLineWrap(true);
        dashboard.setBounds(50, 330, 900, 50);
        panel.add(dashboard);

        // close connection if client terminate the application
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                try {
                    connectionStrategy.closeConnection();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.getWindow().dispose();
            }
        });

        frame.add(panel);

        // set frame visible
        frame.setVisible(true);
    }

    /**
     * @return frame object
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * place the application icon on screen
     * @param panel panel
     */
    private void placeIcons(JPanel panel) {
        ImageIcon icon = new ImageIcon(getClass().getResource(APP_ICON_PATH));
        JLabel label = new JLabel(icon);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setBounds(350, 0, 255, 255);
        panel.add(label);
    }

    /**
     * place the search box and button on screen
     * @param panel panel
     */
    private void placeTextSearch(JPanel panel) {
//        wordTextField = new JTextField(20);
//        wordTextField.setBounds(100, 260, 700, 25);

        wordTextField = new RSyntaxTextArea(1, 60);
        wordTextField.setBounds(100, 260, 700, 28);
        wordTextField.setFont(new Font("Times New Roman", Font.BOLD,25));
        wordTextField.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        wordTextField.setCodeFoldingEnabled(true);
        wordTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    btnSearch.doClick();
                }
            }
        });

        wordTextField.setCaretPosition(0);
        wordTextField.discardAllEdits();
        wordTextField.requestFocusInWindow();
        wordTextField.setMarkOccurrences(true);
        wordTextField.getSyntaxScheme().getStyle(TokenTypes.COMMENT_DOCUMENTATION).background = new java.awt.Color(255, 240, 240);
        wordTextField.setLineWrap(true);
        wordTextField.setWrapStyleWord(true);
        // add auto correction
        URL res = this.getClass().getClassLoader().getResource("resource/english_dic.zip");
        InputStream input = getClass().getResourceAsStream("/resource/english_dic.zip");
        File zip = loadFileFromJar(res, input);
        boolean usEnglish = true; // "false" will use British English
        SpellingParser parser = null;
        try {
            parser = SpellingParser.createEnglishSpellingParser(zip, usEnglish);
        } catch (IOException e) {
            e.printStackTrace();
        }
        wordTextField.addParser(parser);

        panel.add(wordTextField);

        btnSearch = new JButton("Search");
        btnSearch.setBackground(SystemColor.menu);
        btnSearch.setForeground(Color.BLACK);
        btnSearch.setBounds(810, 260, 100, 25);
        btnSearch.addActionListener(e -> {
            wordTextField.setText(wordTextField.getText().replace("\n", ""));
            String word = wordTextField.getText();
            connectionStrategy.connect(word, dashboard, connectionStrategy.generateSearchRequest(word));
        });
        panel.add(btnSearch);
    }

    /**
     * place add word, meaning button on screen
     * @param panel panel
     */
    private void placeAdd(JPanel panel) {
        JButton btnAdd = new JButton("Add Meaning");
        btnAdd.setBackground(SystemColor.menu);
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setBounds(350, 290, 120, 25);
        btnAdd.addActionListener(l -> {
            frame.setEnabled(false);
            frame.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
            AddWordDialog addWordDialog = new AddWordDialog(this, wordTextField.getText());
        });
        panel.add(btnAdd);
    }

    /**
     * place the remove word button on screen
     * @param panel panel
     */
    private void placeRemove(JPanel panel) {
        JButton btnRemove = new JButton("Remove Word");
        btnRemove.setBackground(SystemColor.menu);
        btnRemove.setForeground(Color.BLACK);
        btnRemove.setBounds(500, 290, 120, 25);
        btnRemove.addActionListener(e -> {
            String word = wordTextField.getText();
            connectionStrategy.connect(word, dashboard, connectionStrategy.generateDeleteRequest(word));
        });
        panel.add(btnRemove);
    }

    /**
     * @return client's connection strategy to server
     */
    public IConnectionStrategy getConnectionStrategy() {
        return connectionStrategy;
    }

    /**
     * @return dashboard to display response from server
     */
    public JTextArea getDashboard() {
        return dashboard;
    }
}
