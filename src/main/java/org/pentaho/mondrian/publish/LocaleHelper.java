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

import java.util.Locale;

public class LocaleHelper {

    private static final ThreadLocal<Locale> threadLocales = new ThreadLocal<Locale>();

    private static Locale defaultLocale;

    public static final String UTF_8 = "UTF-8"; //$NON-NLS-1$
    private static String encoding = UTF_8;

    public static final String LEFT_TO_RIGHT = "LTR"; //$NON-NLS-1$
    private static String textDirection = LEFT_TO_RIGHT;

    public static void setDefaultLocale(Locale newLocale) {
        defaultLocale = newLocale;
    }

    public static Locale getDefaultLocale() {
        return defaultLocale;
    }

    public static void setLocale(Locale newLocale) {
        threadLocales.set(newLocale);
    }

    public static Locale getLocale() {
        Locale rtn = (Locale) threadLocales.get();
        if (rtn != null) {
            return rtn;
        }
        defaultLocale = Locale.getDefault();
        setLocale(defaultLocale);
        return defaultLocale;
    }

    public static void setSystemEncoding(String encoding) {
    	LocaleHelper.encoding = encoding;
    }

    public static void setTextDirection(String textDirection) {
        // TODO make this ThreadLocal
    	LocaleHelper.textDirection = textDirection;
    }

    public static String getSystemEncoding() {
        return encoding;
    }

    public static String getTextDirection() {
        // TODO make this ThreadLocal
        return textDirection;
    }

}
