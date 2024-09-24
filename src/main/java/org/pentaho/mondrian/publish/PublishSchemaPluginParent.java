/*
* Copyright 2002 - 2017 Hitachi Vantara.  All rights reserved.
* 
* This software was developed by Hitachi Vantara and is provided under the terms
* of the Mozilla Public License, Version 1.1, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to http://www.mozilla.org/MPL/MPL-1.1.txt. TThe Initial Developer is Pentaho Corporation.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/

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
