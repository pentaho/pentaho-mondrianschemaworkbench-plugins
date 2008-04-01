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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import mondrian.gui.Workbench;
import mondrian.gui.WorkbenchMenubarPlugin;

public class MondrianPublish implements WorkbenchMenubarPlugin {

    private Workbench workbench;
    
    public void addItemsToMenubar(JMenuBar menubar) {
        JMenu menu = getFileMenu(menubar);
        int loc = getFirstMenuSeparator(menu);
        JMenuItem publish = new JMenuItem("Publish...");
        publish.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PublishToServerCommand command = new PublishToServerCommand();
                command.execute(workbench);
            }
        });
        menu.add(publish, loc);
        menu.add(new JSeparator(), loc);
    }
    
    private int getFirstMenuSeparator(JMenu menu) {
        for (int i = 0; i < menu.getMenuComponentCount(); i++) {
            if (menu.getMenuComponent(i) instanceof JSeparator) {
                return i;
            }
        }
        return -1;
    }
    
    private JMenu getFileMenu(JMenuBar menubar) {
        return menubar.getMenu(0);
    }

    public void setWorkbench(Workbench workbench) {
        // TODO Auto-generated method stub
        this.workbench = workbench;
    }
    
}
