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
package org.pentaho.mondrian.publish;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

public class CreateNewRepositoryFolderDialog extends JDialog {

  private static final long serialVersionUID = 5415143680011688917L;
  
  JTextField nameTextField = new JTextField(40);
  JTextField descTextField = new JTextField(40);
  boolean okPressed = false;

  KeyListener submitListener = new KeyListener() {

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
      if (e.getKeyChar() == '\r' || e.getKeyChar() == '\n') {
        if (StringUtils.isEmpty(getName())) {
          JOptionPane.showMessageDialog(CreateNewRepositoryFolderDialog.this, "You must enter a folder name.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
          okPressed = true;
          setVisible(false);
        }
      }
    }
  };

  
  public CreateNewRepositoryFolderDialog(Frame frame) {
    super(frame, true);
    init();
  }

  public CreateNewRepositoryFolderDialog(Dialog dialog) {
    super(dialog, true);
    init();
  }

  public void init() {
    setTitle("Create New Folder");

    getContentPane().setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 0.0;
    c.insets = new Insets(5, 10, 0, 10);
    getContentPane().add(new JLabel("Name:"), c);

    c.gridx = 0;
    c.gridy = 1;
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 1.0;
    c.insets = new Insets(0, 10, 5, 10);
    nameTextField.addKeyListener(submitListener);
    getContentPane().add(nameTextField, c);

    c.gridx = 0;
    c.gridy = 2;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 0.0;
    c.insets = new Insets(5, 10, 0, 10);
    getContentPane().add(new JLabel("Description:"), c);

    c.gridx = 0;
    c.gridy = 3;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 1.0;
    c.insets = new Insets(0, 10, 5, 10);
    descTextField.addKeyListener(submitListener);
    getContentPane().add(descTextField, c);

    c.gridx = 0;
    c.gridy = 4;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.SOUTHEAST;
    c.gridwidth = 2;
    c.weightx = 1.0;
    c.insets = new Insets(10, 2, 2, 2);
    getContentPane().add(buildButtonPanel(), c);

    setResizable(false);
    pack();
  }

  private JPanel buildButtonPanel() {
    JPanel buttonPanel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(0, 0, 0, 2);
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.EAST;
    c.weightx = 1.0;

    JButton okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        okPressed = true;
        setVisible(false);
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

  public String getDescription() {
    return descTextField.getText();
  }

  public String getName() {
    return nameTextField.getText();
  }

  public boolean isOkPressed() {
    return okPressed;
  }

  public void setOkPressed(boolean okPressed) {
    this.okPressed = okPressed;
  }

}