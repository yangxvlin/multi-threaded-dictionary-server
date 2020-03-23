package com.unimelb.comp90015.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.unimelb.comp90015.Client.Constant.APP_ICON_PATH;
import static java.awt.Component.CENTER_ALIGNMENT;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 15:25
 * description:
 **/

public class DictionaryGUI {
    private static JFrame frame;
    private JPanel panel;
    private JTextArea dashboard = new JTextArea();
    private static JTextField wordTextField;

    public DictionaryGUI(String appName) {
        // create JFrame instance
        frame = new JFrame(appName);
        // Setting the width and height of frame
        frame.setSize(1024, 720);
        frame.getContentPane().setBackground(Color.white);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel=new JPanel();
        panel.setLayout(null);
        panel.setBorder(new EmptyBorder(7, 7, 7, 7));
//        panel.setBackground(Color.WHITE);

        placeIcons(panel);
        placeTextSearch(panel);
        placeAddRemove(panel);

        dashboard.setEditable(false);
        dashboard.setBackground(SystemColor.window);
        dashboard.setLineWrap(true);
        dashboard.setBounds(50, 330, 900, 50);
        panel.add(dashboard);

        frame.add(panel);

        // set frame visible
        frame.setVisible(true);
    }

    private static void placeIcons(JPanel panel) {
        ImageIcon icon = new ImageIcon(APP_ICON_PATH);
        JLabel label=new JLabel(icon);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setBounds(350, 0, 255, 255);
        panel.add(label);
    }

    private static void placeTextSearch(JPanel panel) {
        wordTextField = new JTextField(20);
        wordTextField.setBounds(100, 260, 700, 25);
        panel.add(wordTextField);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBackground(SystemColor.menu);
        btnSearch.setForeground(Color.BLACK);
        btnSearch.setBounds(810, 260, 100, 25);
        btnSearch.addActionListener(e -> {
            System.out.println("search: " + wordTextField.getText());
        });
        panel.add(btnSearch);
    }

    private void placeAddRemove(JPanel panel) {

        JButton btnAdd = new JButton("Add Meaning");
        btnAdd.setBackground(SystemColor.menu);
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setBounds(350, 290, 120, 25);
        btnAdd.addActionListener(l -> {
            frame.setEnabled(false);
            frame.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
            AddWordDialog addWordDialog = new AddWordDialog(frame, wordTextField.getText());
        });
        panel.add(btnAdd);

        JButton btnRemove = new JButton("Remove Word");
        btnRemove.setBackground(SystemColor.menu);
        btnRemove.setForeground(Color.BLACK);
        btnRemove.setBounds(500, 290, 120, 25);
        btnRemove.addActionListener(e -> {
            System.out.println("Remove: " + wordTextField.getText());
        });
        panel.add(btnRemove);
    }
}
