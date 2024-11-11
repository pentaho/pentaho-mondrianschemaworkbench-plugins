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
