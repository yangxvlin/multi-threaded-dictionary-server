package com.unimelb.comp90015.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 17:57
 * description: the GUI to add word's meaning and send add task request to server
 **/

public class AddWordDialog extends JFrame {
    /**
     * displaying added meaning on screen
     */
    private static JTextArea meaningDisplay = new JTextArea();

    public AddWordDialog(ClientGUI parent, String word) {
        // configuration setup
        setTitle("Adding word");
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        setBounds(new Rectangle(
            (int) parent.getFrame().getBounds().getX() + 200,
            (int) parent.getFrame().getBounds().getY() + 20,
                (int) parent.getFrame().getBounds().getWidth(),
                (int) parent.getFrame().getBounds().getHeight()
            )
        );

        // label to guide user
        JLabel label = new JLabel("New word: ");
        label.setBounds(10, 10, 50, 25);
        add(label);

        // display client's added meaning to the word
        JTextField wordTextField = new JTextField(20);
        wordTextField.setText(word);
        wordTextField.setBounds(70, 10, 300, 25);
        add(wordTextField);

        // the word's meanings
        ArrayList<String> wordMeanings = new ArrayList<>();

        // the button to add a meaning to the word
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

        // confirm add word with meaning button to send request to server
        JButton btnConfirmNewWord = new JButton("Confirm the new word and its meanings");
        btnConfirmNewWord.setBackground(SystemColor.menu);
        btnConfirmNewWord.setForeground(Color.BLACK);
        btnConfirmNewWord.setBounds(500, 10, 100, 25);
        btnConfirmNewWord.addActionListener(e -> {
                // ask whether client confirms the adding
                int confirmation = JOptionPane.showConfirmDialog(
                    null,
                    meaningDisplay.getText(),
                    "Submit: " + wordTextField.getText(),
                    JOptionPane.OK_CANCEL_OPTION
                );

                switch (confirmation) {
                    // client agree the adding, so send request to server
                    case JOptionPane.YES_OPTION:
                        // submit to server
                        String wordToAdd = wordTextField.getText();
                        String meaning = meaningDisplay.getText();
                        parent.getConnectionStrategy().connect(
                                wordToAdd, meaning,
                                parent.getDashboard(),
                                parent.getConnectionStrategy().generateAddRequest(wordToAdd, meaning)
                        );

                        wordMeanings.clear();
                        meaningDisplay.setText("");
                        parent.getFrame().setEnabled(true);
                        dispose();
                        break;
                    // client cancel the adding, so exits the dialog
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
            // when close the dialog, set parent frame enable
            parent.getFrame().setEnabled(true);
            }
        });

        setVisible(true);
   }
}
