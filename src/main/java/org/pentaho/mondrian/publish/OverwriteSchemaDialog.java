/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.mondrian.publish;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OverwriteSchemaDialog extends JDialog {

  private static final long serialVersionUID = 2477112004003189540L;
  boolean okPressed = false;

  public OverwriteSchemaDialog(Frame frame, String name) {
    super(frame, true);
    init(name);
  }

  public OverwriteSchemaDialog(Dialog dialog, String name) {
    super(dialog, true);
    init(name);
  }

  public void init(String name) {
    setTitle("Overwrite");

    getContentPane().setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.WEST;
    c.insets = new Insets(20, 20, 5, 20);
    getContentPane().add(new JLabel(name + " exists, overwrite?"), c);

    c.gridx = 0;
    c.gridy = 2;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.SOUTHEAST;
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

    JButton okButton = new JButton("Yes");
    okButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        okPressed = true;
        setVisible(false);
      }

    });
    JButton cancelButton = new JButton("No");
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

//  public boolean alsoReplaceXAction() {
//    return replaceCheckBox.isSelected();
//  }
  
  public boolean isOkPressed() {
    return okPressed;
  }

  public void setOkPressed(boolean okPressed) {
    this.okPressed = okPressed;
  }

}
