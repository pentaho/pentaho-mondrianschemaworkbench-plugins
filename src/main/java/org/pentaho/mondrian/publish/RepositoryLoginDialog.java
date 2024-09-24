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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

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
  JTextField jndiDataSourceName = new JTextField("");
  JCheckBox enableXmlaCheckBox = new JCheckBox(Messages.getString("PublishToServerCommand.XMLADataSourceLabel")); //$NON-NLS-1$
  JCheckBox rememberSettings = new JCheckBox("Remember these Settings", true);

  boolean publishPressed = false;
  String serverURL = null;

  List<String> publishLocations = null;
  List<String> publishUserIds = null;
  List<String> publishUserPasswords = null;


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
          publishPressed = true;
          setVisible(false);
        }
      }
    }
  };

  public RepositoryLoginDialog(Frame parent, String serverURL, List<String> publishLocations, List<String> publishUserIds, List<String> publishUserPasswords,  String jndiName, boolean enableXmla) {
    super(parent, "Publish Schema");
    init(serverURL, publishLocations, publishUserIds, publishUserPasswords, jndiName, enableXmla);
  }

  public RepositoryLoginDialog(Dialog parent, String serverURL, List<String> publishLocations, List<String> publishUserIds, List<String> publishUserPasswords,  String jndiName, boolean enableXmla) {
    super(parent, "Publish Schema");
    init(serverURL, publishLocations, publishUserIds, publishUserPasswords, jndiName, enableXmla);
  }

  private void init(String serverURL, List<String> publishLocations, List<String> publishUserIds, List<String> publishUserPasswords, String jndiName, boolean enableXmla) {
    this.serverURL = serverURL;
    this.publishLocations = publishLocations;
    this.publishUserIds = publishUserIds;
    this.publishUserPasswords = publishUserPasswords;

    setModal(true);
    setResizable(false);
    getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(10, 10, 5, 10);
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.BOTH;
    getContentPane().add(buildCredentialsPanel(serverURL), c);

    c.gridy = 1;
    c.insets = new Insets(0, 10, 5, 10);
    getContentPane().add(buildPublishSettingsPanel(jndiName, enableXmla), c);
    c.gridy = 2;

    getContentPane().add(rememberSettings, c);

    c.gridy = 3;
    c.insets = new Insets(5, 10, 5, 10);
    getContentPane().add(buildButtonPanel(), c);
    pack();
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

    JButton publishButton = new JButton("Publish");
    publishButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        if (StringUtils.isEmpty(getServerURL()) || StringUtils.isEmpty(getUsername()) || StringUtils.isEmpty(getUserPassword())) {
          JOptionPane.showMessageDialog(RepositoryLoginDialog.this, "You must provide a username/password and server URL.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
          publishPressed = true;
          setVisible(false);
        }
      }

    });

    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        publishPressed = false;
        setVisible(false);
      }

    });

    buttonPanel.add(publishButton, c);
    c.gridx = 1;
    c.weightx = 0;
    c.insets = new Insets(0, 0, 0, 0);
    buttonPanel.add(cancelButton, c);

    return buttonPanel;
  }

  private JPanel buildCredentialsPanel(String serverURL) {
    JPanel credentialsPanel = new JPanel(new GridBagLayout());
    credentialsPanel.setBorder(BorderFactory.createTitledBorder("Pentaho Credentials"));

    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(0, 20, 5, 20);
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.HORIZONTAL;

    JLabel urlLabel = new JLabel("Server URL:");
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
          userPasswordField.setText(publishUserPasswords.get(index));
        }
      }

    });

    credentialsPanel.add(urlLabel, c);
    c.gridy = 1;
    c.insets = new Insets(0, 20, 0, 20);
    credentialsPanel.add(urlCombo, c);
    c.gridy = 2;
    c.insets = new Insets(0, 20, 0, 20);
    credentialsPanel.add(buildUserPanel(), c);

    c.gridy = 3;
    c.insets = new Insets(0, 20, 10, 20);

    return credentialsPanel;
  }

  private JPanel buildUserPanel() {
    JPanel userPanel = new JPanel(new GridBagLayout());
    JLabel userLabel = new JLabel("User:");
    JLabel passwordLabel = new JLabel("Password:");
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    userPanel.add(userLabel, c);
    c.gridy = 1;
    int index = publishLocations.indexOf(serverURL);
    if (index >= 0 && index < publishUserIds.size()) {
      userField.setText(publishUserIds.get(index));
    }
    userField.addKeyListener(submitListener);
    userPanel.add(userField, c);
    c.gridy = 2;
    userPanel.add(passwordLabel, c);
    c.gridy = 3;
    userPasswordField.addKeyListener(submitListener);
    index = publishLocations.indexOf(serverURL);
    if (index >= 0 && index < publishUserPasswords.size()) {
      userPasswordField.setText(publishUserPasswords.get(index));
    }
    userPanel.add(userPasswordField, c);
    return userPanel;
  }

  private JPanel buildPublishSettingsPanel(String jndiName, boolean enableXmla) {
    JPanel publishSettingsPanel = new JPanel(new GridBagLayout());
    publishSettingsPanel.setBorder(BorderFactory.createTitledBorder(Messages.getString("PublishToServerCommand.PublishSettingsTitle"))); //$NON-NLS-1$
    GridBagConstraints c = new GridBagConstraints();

    c.insets = new Insets(5, 5, 0, 5);
    c.gridwidth = 2;
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.WEST;
    publishSettingsPanel.add(new JLabel(Messages.getString("PublishToServerCommand.DatasourceLabel")), c); //$NON-NLS-1$

    c.insets = new Insets(5, 5, 5, 0);
    c.gridwidth = 1;
    c.gridx = 0;
    c.gridy = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 1.0;
    jndiDataSourceName.setText(jndiName);
    publishSettingsPanel.add(jndiDataSourceName, c);

    enableXmlaCheckBox.setSelected(enableXmla);
    c.gridwidth = 2;
    c.insets = new Insets(5, 5, 5, 5);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.WEST;
    c.gridx = 0;
    c.gridy = 2;
    c.weightx = 1.0;
    publishSettingsPanel.add(enableXmlaCheckBox, c);

    c.weightx = 0.0;
    c.insets = new Insets(5, 5, 5, 5);
    c.gridx = 0;
    c.gridy = 3;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.WEST;
    getContentPane().add(publishSettingsPanel, c);

    return publishSettingsPanel;
  }

  public boolean isPublishPressed() {
    return publishPressed;
  }

  public void setPublishPressed(boolean publishPressed) {
    this.publishPressed = publishPressed;
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

  public boolean getRememberSettings() {
    return rememberSettings.isSelected();
  }

  public String getJndiDataSourceName() {
    return jndiDataSourceName.getText();
  }

  public boolean getEnableXmla() {
    return enableXmlaCheckBox.isSelected();
  }
}
