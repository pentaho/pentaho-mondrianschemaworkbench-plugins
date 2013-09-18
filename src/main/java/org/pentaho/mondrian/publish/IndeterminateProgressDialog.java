/*
* Copyright 2002 - 2013 Pentaho Corporation.  All rights reserved.
* 
* This software was developed by Pentaho Corporation and is provided under the terms
* of the Mozilla Public License, Version 1.1, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to http://www.mozilla.org/MPL/MPL-1.1.txt. TThe Initial Developer is Pentaho Corporation.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/

package org.pentaho.mondrian.publish;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

/**
 * This class displays a modal indeterminate progress bar window  
 * 
 * @author Will Gorman (wgorman@pentaho.com)
 */
public class IndeterminateProgressDialog extends JDialog {

    private static final long serialVersionUID = 5591242587651404343L;

    public IndeterminateProgressDialog(Frame owner, String title, String message) {
        super(owner, title);
        BorderLayout layout = new BorderLayout();
        layout.setHgap(10);
        layout.setVgap(10);
        JPanel panel = new JPanel(layout);
        panel.setBorder(new EmptyBorder(10,10,10,10));
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        panel.add(new JLabel(message), BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);
        add(panel);
        pack();
        setModal(true);
    }
}
