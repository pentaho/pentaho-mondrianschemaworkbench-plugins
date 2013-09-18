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

public class PublishException extends Exception {

    private static final long serialVersionUID = -5888073686950909654L;

    public PublishException(String message) {
        super(message);
    }

    public PublishException(Throwable cause) {
        super(cause);
    }

    public PublishException(String message, Throwable cause) {
        super(message, cause);
    }
}
