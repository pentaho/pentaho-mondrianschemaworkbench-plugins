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

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.dom4j.Document;

public class SolutionRepositoryTree extends JTree {

  private static final long serialVersionUID = 7264000700096597144L;
  
  boolean showFoldersOnly = true;
  String filters[] = null;
  String clientFilters[] = null;
  Document solutionRepositoryDocument = null;
  String selectedPath = null;
  String baseURL = null;
  String serverUserId = null;
  String serverPassword = null;
  RepositoryHelper repositoryHelper = RepositoryHelper.getInstance();
  
  public SolutionRepositoryTree(boolean showFoldersOnly, String baseURL, String[] filters, String serverUserId, String serverPassword) throws Exception {
    this.showFoldersOnly = showFoldersOnly;
    this.filters = filters;
    this.baseURL = baseURL;
    this.serverUserId = serverUserId;
    this.serverPassword = serverPassword;
    setModel(new DefaultTreeModel(repositoryHelper.getRootNode()));
    solutionRepositoryDocument = RepositoryHelper.getRepositoryDocument(baseURL, filters, serverUserId, serverPassword);
    repositoryHelper.buildRepositoryTree(solutionRepositoryDocument, filters, showFoldersOnly);

    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
      public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel component = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (value instanceof DefaultMutableTreeNode) {
          DefaultMutableTreeNode node = ((DefaultMutableTreeNode) value);
          if (leaf && repositoryHelper.isFolder(node)) {
            component.setIcon(getOpenIcon());
          }
        }
        return component;
      }
    };
    if (showFoldersOnly) {
      renderer.setLeafIcon(renderer.getOpenIcon());
    }
    setCellRenderer(renderer);
  }

  public void setSelectedPath(String path) {
    selectedPath = path;
    StringTokenizer st = new StringTokenizer(path, "/");
    DefaultMutableTreeNode node = repositoryHelper.getRootNode();
    List<TreeNode> pathList = new ArrayList<TreeNode>();
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      DefaultMutableTreeNode child = null;
      for (int i = 0; i < node.getChildCount(); i++) {
        child = (DefaultMutableTreeNode) node.getChildAt(i);
        if (child.getUserObject().equals(token)) {
          break;
        }
      }
      node = child;
      if (child != null) {
        pathList.add(child);
      }
    }

    if (pathList.size() > 0) {
      TreeNode[] tn = new TreeNode[pathList.size() + 1];
      tn[0] = repositoryHelper.getRootNode();
      for (int i = 0; i < tn.length-1; i++) {
        tn[i + 1] = pathList.get(i);
      }

      TreePath tp = new TreePath(tn);
//      setExpandedState(tp, true);
      scrollPathToVisible(tp);
      setSelectionPath(tp);
    }
  }

  public String getSelectedPath() {
    return selectedPath;
  }

  public String[] getClientFilters() {
    return clientFilters;
  }

  public void setClientFilters(String clientFilters[]) {
    this.clientFilters = clientFilters;
    // fire tree rebuilding exercise obeying the clientFilter
    repositoryHelper.buildRepositoryTree(solutionRepositoryDocument, clientFilters, showFoldersOnly);
    setModel(new DefaultTreeModel(repositoryHelper.getRootNode()));
    setSelectedPath(selectedPath);
  }

  public void refresh(boolean fetchDocument) throws Exception {
    if (fetchDocument) {
      solutionRepositoryDocument = RepositoryHelper.getRepositoryDocument(baseURL, filters, serverUserId, serverPassword);
    }
    repositoryHelper.buildRepositoryTree(solutionRepositoryDocument, clientFilters, showFoldersOnly);
    setModel(new DefaultTreeModel(repositoryHelper.getRootNode()));
    repaint();
  }

}
