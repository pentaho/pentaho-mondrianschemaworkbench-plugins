/*
 * Copyright 2008 Pentaho Corporation.  All rights reserved.
 * This software was developed by Pentaho Corporation and is provided under the terms
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use
 * this file except in compliance with the license. If you need a copy of the license,
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
 * the license for the specific language governing your rights and limitations.
 */
package org.pentaho.mondrian.workbench.publish;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

public class RepositoryLoginDialog extends JDialog {

  private static final long serialVersionUID = 2458668776266464325L;
  
  JComboBox urlCombo = new JComboBox();
  JTextField userField = new JTextField(25);
  JPasswordField userPasswordField = new JPasswordField();
  JPasswordField publishPasswordField = new JPasswordField(25);
  JCheckBox rememberSettings = new JCheckBox("Remember these Settings", true);
  boolean okPressed = false;
  List<String> publishLocations = null;
  List<String> publishUserIds = null;
  List<String> publishUserPasswords = null;
  List<String> publishPasswords = null;
  String serverURL = null;

  KeyListener submitListener = new KeyListener() {

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
      if (e.getKeyChar() == '\r' || e.getKeyChar() == '\n') {
        if (StringUtils.isEmpty(getServerURL()) || StringUtils.isEmpty(getUsername()) || StringUtils.isEmpty(getUserPassword())) {
          JOptionPane.showMessageDialog(RepositoryLoginDialog.this, "You must provide a username/password and server URL.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
          okPressed = true;
          setVisible(false);
        }
      }
    }
  };

  public RepositoryLoginDialog(Frame parent, String serverURL, List<String> publishLocations, List<String> publishUserIds, List<String> publishUserPasswords, List<String> publishPasswords) {
    super(parent, "Repository Login");
    init(serverURL, publishLocations, publishUserIds, publishUserPasswords, publishPasswords);
  }

  public RepositoryLoginDialog(Dialog parent, String serverURL, List<String> publishLocations, List<String> publishUserIds, List<String> publishUserPasswords, List<String> publishPasswords) {
    super(parent, "Repository Login");
    init(serverURL, publishLocations, publishUserIds, publishUserPasswords, publishPasswords);
  }

  private void init(String serverURL, List<String> publishLocations, List<String> publishUserIds, List<String> publishUserPasswords, List<String> publishPasswords) {
    this.serverURL = serverURL;
    this.publishLocations = publishLocations;
    this.publishUserIds = publishUserIds;
    this.publishUserPasswords = publishUserPasswords;
    this.publishPasswords = publishPasswords;
    setModal(true);
    setResizable(false);
    getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(10, 10, 5, 10);
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.BOTH;
    getContentPane().add(buildServerPanel(serverURL), c);
    c.gridy = 1;
    c.insets = new Insets(0, 10, 5, 10);
    getContentPane().add(buildUserPanel(), c);
    c.gridy = 2;
    getContentPane().add(rememberSettings, c);

    c.gridy = 3;
    c.insets = new Insets(5, 10, 5, 10);
    getContentPane().add(buildButtonPanel(), c);
    pack();
    publishPasswordField.requestFocus();
  }

  private JPanel buildButtonPanel() {
    JPanel buttonPanel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(0, 0, 0, 5);
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.EAST;
    c.weightx = 1.0;

    JButton okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        if (StringUtils.isEmpty(getServerURL()) || StringUtils.isEmpty(getUsername()) || StringUtils.isEmpty(getUserPassword())) {
          JOptionPane.showMessageDialog(RepositoryLoginDialog.this, "You must provide a username/password and server URL.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
          okPressed = true;
          setVisible(false);
        }
      }

    });
    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        okPressed = false;
        setVisible(false);
      }

    });

    buttonPanel.add(okButton, c);
    c.gridx = 1;
    c.weightx = 0;
    c.insets = new Insets(0, 0, 0, 0);
    buttonPanel.add(cancelButton, c);

    return buttonPanel;
  }

  private JPanel buildServerPanel(String serverURL) {
    JPanel serverPanel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(0, 20, 5, 20);
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.HORIZONTAL;

    JLabel urlLabel = new JLabel("URL:");
    urlCombo.setEditable(true);
    urlCombo.setModel(new DefaultComboBoxModel(publishLocations.toArray()));
    urlCombo.setSelectedItem(serverURL);
    urlCombo.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        RepositoryLoginDialog.this.serverURL = (String) urlCombo.getSelectedItem();
        // fill out the other fields
        int index = RepositoryLoginDialog.this.publishLocations.indexOf(RepositoryLoginDialog.this.serverURL);
        if (index >= 0 && index < publishUserIds.size()) {
          userField.setText(publishUserIds.get(index));
        }
      }

    });
    JLabel passwordLabel = new JLabel("Publish Password:");
    publishPasswordField.addKeyListener(submitListener);

    serverPanel.add(urlLabel, c);
    c.gridy = 1;
    c.insets = new Insets(0, 20, 0, 20);
    serverPanel.add(urlCombo, c);
    c.gridy = 2;
    c.insets = new Insets(0, 20, 0, 20);
    serverPanel.add(passwordLabel, c);
    c.gridy = 3;
    c.insets = new Insets(0, 20, 10, 20);
    int index = publishLocations.indexOf(serverURL);
    if (index >= 0 && index < publishPasswords.size()) {
      publishPasswordField.setText(publishPasswords.get(index));
    } else {
      // Add code to check for a properties file containing the default
      // publish password. For this to work, the file needs to be located in the
      // lib directory if it is to be found.
      try {
        ResourceBundle bundle = ResourceBundle.getBundle("publishpassword"); //$NON-NLS-1$
        String defaultPassword = bundle.getString("default.password"); //$NON-NLS-1$
        if ( (defaultPassword != null) && (defaultPassword.length() > 0) ) {
          // Messagebox to show where the file was able to be found.
          // JOptionPane.showMessageDialog(null, "Default Password: " + defaultPassword);
          publishPasswordField.setText(defaultPassword);
        }
      } catch (Exception ex) {
        // No publishpassword.properties
      }
    }
    serverPanel.add(publishPasswordField, c);

    serverPanel.setBorder(BorderFactory.createTitledBorder("Server"));
    return serverPanel;
  }

  private JPanel buildUserPanel() {
    JPanel userPanel = new JPanel(new GridBagLayout());
    userPanel.setBorder(BorderFactory.createTitledBorder("Pentaho Credentials"));
    JLabel userLabel = new JLabel("User:");
    JLabel passwordLabel = new JLabel("Password:");
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(0, 20, 5, 20);
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    userPanel.add(userLabel, c);
    c.gridy = 1;
    c.insets = new Insets(0, 20, 0, 20);
    int index = publishLocations.indexOf(serverURL);
    if (index >= 0 && index < publishUserIds.size()) {
      userField.setText(publishUserIds.get(index));
    }
    userField.addKeyListener(submitListener);
    userPanel.add(userField, c);
    c.gridy = 2;
    c.insets = new Insets(0, 20, 0, 20);
    userPanel.add(passwordLabel, c);
    c.gridy = 3;
    c.insets = new Insets(0, 20, 10, 20);
    userPasswordField.addKeyListener(submitListener);
    index = publishLocations.indexOf(serverURL);
    if (index >= 0 && index < publishUserPasswords.size()) {
      userPasswordField.setText(publishUserPasswords.get(index));
    }
    userPanel.add(userPasswordField, c);
    return userPanel;
  }

  public boolean isOkPressed() {
    return okPressed;
  }

  public void setOkPressed(boolean okPressed) {
    this.okPressed = okPressed;
  }

  public String getServerURL() {
    return urlCombo.getSelectedItem().toString();
  }

  public String getUsername() {
    return userField.getText();
  }

  public String getUserPassword() {
    return new String(userPasswordField.getPassword());
  }

  public String getPublishPassword() {
    return new String(publishPasswordField.getPassword());
  }

  public boolean getRememberSettings() {
    return rememberSettings.isSelected();
  }

}
