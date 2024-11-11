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
