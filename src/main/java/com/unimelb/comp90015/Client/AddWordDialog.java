package com.unimelb.comp90015.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 17:57
 * description:
 **/

public class AddWordDialog extends JFrame {
    private static JTextArea meaningDisplay = new JTextArea();

    public AddWordDialog(DictionaryGUI parent, String word) {
        setTitle("Adding word");
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
//        setAlwaysOnTop(true);
        setBounds(new Rectangle(
            (int) parent.getFrame().getBounds().getX() + 200,
            (int) parent.getFrame().getBounds().getY() + 20,
                (int) parent.getFrame().getBounds().getWidth(),
                (int) parent.getFrame().getBounds().getHeight()
            )
        );

        JLabel label = new JLabel("New word: ");
        label.setBounds(10, 10, 50, 25);
        add(label);

        JTextField wordTextField = new JTextField(20);
        wordTextField.setText(word);
        wordTextField.setBounds(70, 10, 300, 25);
        add(wordTextField);

        ArrayList<String> wordMeanings = new ArrayList<>();


        JButton btnAddAMeaning = new JButton("Add a meaning");
        btnAddAMeaning.setBackground(SystemColor.menu);
        btnAddAMeaning.setForeground(Color.BLACK);
        btnAddAMeaning.setBounds(400, 10, 100, 25);
        btnAddAMeaning.addActionListener(e -> {
                String meaning = JOptionPane.showInputDialog(
                        null,
                        "What is the meaning of '" + wordTextField.getText() + "' ?",
                        "Add a meaning for word",
                        JOptionPane.QUESTION_MESSAGE);
                if (meaning != null && !meaning.isEmpty()) {
                    wordMeanings.add(meaning);
                    meaningDisplay.append(wordMeanings.size() + ". " + meaning + "\n");
                }
            }
        );
        add(btnAddAMeaning);

        JButton btnConfirmNewWord = new JButton("Confirm the new word and its meanings");
        btnConfirmNewWord.setBackground(SystemColor.menu);
        btnConfirmNewWord.setForeground(Color.BLACK);
        btnConfirmNewWord.setBounds(500, 10, 100, 25);
        btnConfirmNewWord.addActionListener(e -> {
                int confirmation = JOptionPane.showConfirmDialog(
                    null,
                    meaningDisplay.getText(),
                    "Submit: " + wordTextField.getText(),
                    JOptionPane.OK_CANCEL_OPTION
                );

                switch (confirmation) {
                    case JOptionPane.YES_OPTION:
                        // TODO submit to server
                        String wordToAdd = wordTextField.getText();
                        String meaning = meaningDisplay.getText();
                        String response = parent.getConnectionStrategy().addConnection(wordToAdd, meaning);
                        parent.getDashboard().setText(response);

                        wordMeanings.clear();
                        meaningDisplay.setText("");
                        parent.getFrame().setEnabled(true);
                        dispose();
                        break;
                    case JOptionPane.CANCEL_OPTION:
                    case JOptionPane.CLOSED_OPTION:
                        wordMeanings.clear();
                        meaningDisplay.setText("");
                        parent.getFrame().setEnabled(true);
                        dispose();
                }
            }
        );
        add(btnConfirmNewWord);

        meaningDisplay.setEditable(false);
        meaningDisplay.setBackground(SystemColor.window);
        meaningDisplay.setLineWrap(true);
        meaningDisplay.setBounds(10, 100, 1000, 500);
        add(meaningDisplay);

        addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e){
            //设置启用
            parent.getFrame().setEnabled(true);
            }
        });

        setVisible(true);
   }
}
