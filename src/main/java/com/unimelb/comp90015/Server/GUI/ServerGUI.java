package com.unimelb.comp90015.Server.GUI;

import com.unimelb.comp90015.Server.Dictionary.IDictionary;
import com.unimelb.comp90015.Server.ThreadPool.ThreadPool;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-04-13 21:57
 * description:
 **/

public class ServerGUI {
    /**
     * button to shutdown the server
     */
    private JButton shutdownButton;

    /**
     * display number of tasks being queued
     */
    private JTextArea queuedTasksDisplay;

    /**
     * panel
     */
    private JPanel panel;

    /**
     * refresh the status of the server
     */
    private JButton refreshButton;

    public ServerGUI(ThreadPool threadPool, IDictionary dictionary) {
        JFrame frame = new JFrame("Server");
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setBorder(new EmptyBorder(7, 7, 7, 7));

        shutdownButton.addActionListener(e -> {
            threadPool.shutdown();
            dictionary.save();
            System.exit(0);
        });

        // click the shutdown if click the exit
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shutdownButton.doClick();
                e.getWindow().dispose();
            }
        });

        refreshButton.addActionListener(e -> {
            queuedTasksDisplay.setText(Integer.toString(threadPool.getQueueSize()));
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
