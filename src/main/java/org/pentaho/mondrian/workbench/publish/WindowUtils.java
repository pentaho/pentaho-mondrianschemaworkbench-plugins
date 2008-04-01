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
 *
 * Additional Contributor(s): Martin Schmid gridvision engineering GmbH
 */
package org.pentaho.mondrian.workbench.publish;

import java.applet.Applet;
import java.awt.*;

/**
 * User: Martin
 * Date: 25.09.2007
 * Time: 21:19:51
 */
public class WindowUtils {
    
    private WindowUtils() {
    }

    public static void setLocationRelativeTo(Window w, Component c) {
        Container root = null;

        if (c != null) {
            if (c instanceof Window || c instanceof Applet) {
                root = (Container) c;
            } else {
                Container parent;
                for (parent = c.getParent(); parent != null; parent = parent.getParent()) {
                    if (parent instanceof Window || parent instanceof Applet) {
                        root = parent;
                        break;
                    }
                }
            }
        }

        if ((c != null && !c.isShowing()) || root == null || !root.isShowing()) {
            Dimension paneSize = w.getSize();
            Dimension screenSize = w.getToolkit().getScreenSize();

            w.setLocation((screenSize.width - paneSize.width) / 2,
                          (screenSize.height - paneSize.height) / 2);
        } else {
            Dimension invokerSize = c.getSize();
            Point invokerScreenLocation = c.getLocationOnScreen();

            Rectangle windowBounds = w.getBounds();
            int dx = invokerScreenLocation.x + ((invokerSize.width - windowBounds.width) >> 1);
            int dy = invokerScreenLocation.y + ((invokerSize.height - windowBounds.height) >> 1);
            Rectangle ss = root.getGraphicsConfiguration().getBounds();

            final Insets screenInsets = w.getToolkit().getScreenInsets(root.getGraphicsConfiguration());

            // Adjust for bottom edge being offscreen
            if (dy + windowBounds.height > ss.height) {
                dy = ss.height - windowBounds.height - screenInsets.bottom;
                if (invokerScreenLocation.x - ss.x + invokerSize.width / 2 < ss.width / 2) {
                    dx = invokerScreenLocation.x + invokerSize.width;
                } else {
                    dx = invokerScreenLocation.x - windowBounds.width;
                }
            }

            // Avoid being placed off the edge of the screen
            if (dx + windowBounds.width > ss.x + ss.width - screenInsets.right) {
                dx = ss.x + ss.width - windowBounds.width - screenInsets.right;
            }
            if (dx < ss.x - screenInsets.left) {
                dx = screenInsets.left;
            }
            if (dy < ss.y - screenInsets.right) {
                dy = screenInsets.top;
            }

            w.setLocation(dx, dy);
        }
    }
}
