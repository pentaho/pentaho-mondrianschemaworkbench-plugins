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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeNode;

import org.apache.commons.lang.StringUtils;

public class SolutionRepositoryTreeDialog extends JDialog {

  private static final long serialVersionUID = 2020339599902159445L;
  
  String publishLocation = "";
  Component parent;
  boolean okPressed = false;
  RepositoryHelper repositoryHelper = RepositoryHelper.getInstance();

  public SolutionRepositoryTreeDialog(final Dialog parent, boolean showFoldersOnly, String webPublishURL, String publishLocation, String userid, String password, String[] filters) throws Exception {
    super(parent);
    this.parent = parent;
    init(showFoldersOnly, webPublishURL, publishLocation, userid, password, filters);
  }

  public SolutionRepositoryTreeDialog(final Frame parent, boolean showFoldersOnly, String webPublishURL, String publishLocation, String userid, String password, String[] filters) throws Exception {
    super(parent);
    this.parent = parent;
    init(showFoldersOnly, webPublishURL, publishLocation, userid, password, filters);
  }

  public void init(boolean showFoldersOnly, final String webPublishURL, String publishLocation, String userid, String password, String[] filters) throws Exception {
    setModal(true);
    setTitle("Browse");
    this.publishLocation = publishLocation;

    final SolutionRepositoryTree repositoryBrowser = new SolutionRepositoryTree(showFoldersOnly, webPublishURL, filters, userid, password);
    JScrollPane treeView = new JScrollPane(repositoryBrowser);

    repositoryBrowser.setSelectedPath(publishLocation);

    JPanel buttonPanel = new JPanel(new GridBagLayout());
    JButton okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        String path = "";
        if (repositoryBrowser.getSelectionPath() != null) {
          for (int i = 1; i < repositoryBrowser.getSelectionPath().getPathCount(); i++) {
            path += "/" + repositoryBrowser.getSelectionPath().getPath()[i];
          }
        }
        setPublishLocation(path);
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

    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(5,5,5,2);
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.NONE;
    c.weightx = 1.0;
    c.anchor = GridBagConstraints.EAST;
    buttonPanel.add(okButton, c);
    c.gridx = 1;
    c.weightx = 0;
    c.insets = new Insets(5,0,5,5);
    buttonPanel.add(cancelButton, c);

    repositoryBrowser.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() > 1) {
          String path = "";
          if (repositoryBrowser.getSelectionPath() != null) {
            if (repositoryBrowser.isExpanded(repositoryBrowser.getSelectionPath()) || ((TreeNode) repositoryBrowser.getLastSelectedPathComponent()).isLeaf()) {
              for (int i = 1; i < repositoryBrowser.getSelectionPath().getPathCount(); i++) {
                path += "/" + repositoryBrowser.getSelectionPath().getPath()[i];
              }
              setPublishLocation(path);
              okPressed = true;
              setVisible(false);
            }
          }
        }
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }

    });

    JPanel newFolderButtonPanel = new JPanel(new BorderLayout());
    ImageIcon icon = new ImageIcon(SolutionRepositoryTree.class.getResource("/images/newfolder.png"));
    final JLabel newFolder = new JLabel(icon);
    newFolder.setToolTipText("Create New Folder...");
    newFolder.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    newFolder.addMouseListener(new MouseListener() {

      public void mouseClicked(MouseEvent e) {
        String path = "";
        if (repositoryBrowser.getSelectionPath() != null) {
          for (int i = 1; i < repositoryBrowser.getSelectionPath().getPathCount(); i++) {
            path += "/" + repositoryBrowser.getSelectionPath().getPath()[i];
          }
        }
        CreateNewRepositoryFolderDialog newFolderDialog = new CreateNewRepositoryFolderDialog(SolutionRepositoryTreeDialog.this);
        Dimension paneSize = newFolderDialog.getSize();
        Dimension screenSize = newFolderDialog.getToolkit().getScreenSize();
        newFolderDialog.setLocation((screenSize.width - paneSize.width) / 2, (screenSize.height - paneSize.height) / 2);

        newFolderDialog.setVisible(true);
        if (newFolderDialog.isOkPressed()) {
          if (!StringUtils.isEmpty(newFolderDialog.getName())) {
            try {
              SwingUtilities.getRootPane(SolutionRepositoryTreeDialog.this).getGlassPane().setVisible(true);
              SwingUtilities.getRootPane(SolutionRepositoryTreeDialog.this).getGlassPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
              repositoryHelper.createNewFolder(webPublishURL, path, newFolderDialog.getName(), newFolderDialog.getDescription());
              repositoryBrowser.refresh(true);
              repositoryBrowser.setSelectedPath(path + "/" + newFolderDialog.getName());
            } catch (Exception e1) {
              e1.printStackTrace();
            } finally {
              SwingUtilities.getRootPane(SolutionRepositoryTreeDialog.this).getGlassPane().setVisible(false);
              SwingUtilities.getRootPane(SolutionRepositoryTreeDialog.this).getGlassPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
          }
        }
      }

      public void mouseEntered(MouseEvent e) {
        newFolder.setBorder(BorderFactory.createEtchedBorder());
      }

      public void mouseExited(MouseEvent e) {
        newFolder.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      }

      public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
      }

      public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
      }

    });
    newFolderButtonPanel.add(newFolder, BorderLayout.EAST);
    newFolderButtonPanel.add(new JLabel("Select where you want to place the report:"), BorderLayout.WEST);
    
    getContentPane().setLayout(new GridBagLayout());
    c.insets = new Insets(2,10,0,10);
    c.anchor = GridBagConstraints.WEST;
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    getContentPane().add(newFolderButtonPanel, c);
    c.insets = new Insets(0,10,5,10);
    c.gridx = 0;
    c.gridy = 1;
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1.0;
    c.weighty = 1.0;
    getContentPane().add(treeView, c);
    c.insets = new Insets(0,10,5,5);
    c.weightx = 0;
    c.weighty = 0;
    c.gridy = 2;
    c.fill = GridBagConstraints.HORIZONTAL;
    getContentPane().add(buttonPanel, c);

    pack();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }

  public String getPublishLocation() {
    return publishLocation;
  }

  public void setPublishLocation(String publishLocation) {
    this.publishLocation = publishLocation;
  }

  public boolean isOkPressed() {
    return okPressed;
  }

  public void setOkPressed(boolean okPressed) {
    this.okPressed = okPressed;
  }
}
