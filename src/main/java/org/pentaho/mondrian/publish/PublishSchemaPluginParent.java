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

import java.io.File;
import javax.swing.JFrame;

/**
 * This interface allows various clients to add Mondrian Publish to Hitachi Vantara
 * Platform capabilities within their application.
 * 
 * @author Will Gorman (wgorman@pentaho.com)
 *
 */
public interface PublishSchemaPluginParent {
    
    /**
     * triggers the storage of properties
     */
    public void storeProperties();
    
    /**
     * gets a property from the host app
     * 
     * @param name property name
     * @return value
     */
    public String getProperty(String name);
    
    /**
     * sets a property to the host app
     * 
     * @param name property name
     * @param value value to set
     */
    public void setProperty(String name, String value);
    
    /**
     * returns the parent window of the dialogs
     * 
     * @return frame
     */
    public JFrame getFrame();
    
    /**
     * returns the name of the schema to publish
     * 
     * @return schema name
     */
    String getSchemaName();
    
    /**
     * returns a file object of the schema to publish
     * 
     * @return file location
     */
    File getSchemaFile();
}
