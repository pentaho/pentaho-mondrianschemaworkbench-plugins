/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.mondrian.publish.workbench;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import org.pentaho.mondrian.publish.Messages;
import org.pentaho.mondrian.publish.PublishSchemaPluginParent;
import org.pentaho.mondrian.publish.PublishToServerCommand;

import mondrian.gui.SchemaExplorer;
import mondrian.gui.Workbench;
import mondrian.gui.WorkbenchMenubarPlugin;

/**
 * This class acts as a bridge from Mondrian Schema Workbench to the Mondrian Publishing 
 * Plugin component.
 * 
 * @author Will Gorman (wgorman@pentaho.com)
 *
 */
public class MondrianPublish implements WorkbenchMenubarPlugin, PublishSchemaPluginParent {

    private static final Logger LOG = Logger.getLogger(PublishToServerCommand.class.getName());
    
    private Workbench workbench;
    
    public MondrianPublish() {
        
    }
    
    public void setWorkbench(Workbench workbench) {
        // TODO Auto-generated method stub
        this.workbench = workbench;
    }
    
    
    /**
     * Main api call from Workbench
     */
    public void addItemsToMenubar(JMenuBar menubar) {
        JMenu menu = menubar.getMenu(0);
        int loc = getFirstMenuSeparator(menu);
        JMenuItem publish = new JMenuItem("Publish...");
        
        // publish action on main menu bar
        publish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // first, verify we have a schema to publish
                SchemaExplorer schemaExplorer = workbench.getCurrentSchemaExplorer();
                if (schemaExplorer == null) {
                    JOptionPane.showMessageDialog(workbench, Messages.getString("NoSchemaSelectedWarning.Message"), Messages.getString("NoSchemaSelectedWarning.Title"), JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (schemaExplorer.isDirty()) {
                    int option = JOptionPane.showConfirmDialog(workbench, Messages.getString("SchemaModifiedWarning.Message"), Messages.getString("SchemaModifiedWarning.Title"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (option == JOptionPane.YES_OPTION) {
                        workbench.saveMenuItemActionPerformed(null);
                    } else if (option == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                
                // second, display the various dialogs and publish the schema
                PublishToServerCommand command = new PublishToServerCommand();
                command.execute(MondrianPublish.this);
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
    


    /**
     * Below are the API calls available to the Publish
     * Dialog
     */

    
    public JFrame getFrame() {
        return workbench;
    }

    public String getProperty(String name) {
        return workbench.getWorkbenchProperty(name);
    }

    public void setProperty(String name, String value) {
        workbench.setWorkbenchProperty(name, value);
    }

    public void storeProperties() {
        workbench.storeWorkbenchProperties();
    }

    public String getSchemaName() {
        return workbench.getCurrentSchemaExplorer().getSchema().name;
    }
    
    public File getSchemaFile() {
        return workbench.getCurrentSchemaExplorer().getSchemaFile();
    }
    
}
