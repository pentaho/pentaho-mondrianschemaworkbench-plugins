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

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import org.apache.commons.lang.StringUtils;

import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Created by gmoran on 5/13/14.
 */
public class PublishUtil {

  private static final Logger LOG = Logger.getLogger(PublishUtil.class.getName());

  private static final String RESERVED_CHARS_URL = "api/repo/files/reservedCharacters";
  private static final String RESERVED_CHARS_URL_DISPLAY="api/repo/files/reservedCharactersDisplay";

  protected static String reservedChars = "/\\\t\r\n";

  protected static String reservedCharsDisplay = "/, \\, TAB, CR, LF";

  private static Pattern containsReservedCharsPattern = makePattern(reservedChars);


  private static Pattern makePattern(String reservedChars)
  {
    // escape all reserved characters as they may have special meaning to regex engine
    StringBuilder buf = new StringBuilder();
    buf.append(".*["); //$NON-NLS-1$
    for (int i=0;i<reservedChars.length();i++)
    {
      buf.append( "\\" ); //$NON-NLS-1$
      buf.append(reservedChars.substring(i, i + 1));
    }
    buf.append("]+.*"); //$NON-NLS-1$
    return Pattern.compile(buf.toString());
  }

  /**
   * Checks for presence of black listed chars as well as illegal permutations of legal chars.
   */
  public static boolean validateName(final String name)
  {
    return !StringUtils.isEmpty( name ) &&
        name.trim().equals( name ) && // no leading or trailing whitespace
        !containsReservedCharsPattern.matcher( name ).matches() && // no reserved characters
        !".".equals( name ) && // no . //$NON-NLS-1$
        !"..".equals( name ) ; // no .. //$NON-NLS-1$
  }

  public static void setReservedChars(String reservedChars)
  {
    containsReservedCharsPattern = makePattern( reservedChars );
  }

  public static Pattern getPattern()
  {
    return containsReservedCharsPattern;
  }

  public static String getReservedCharsDisplay()
  {
    return reservedCharsDisplay;
  }

  public static void setReservedCharsDisplay(String reservedCharsDisplay)
  {
    PublishUtil.reservedCharsDisplay = reservedCharsDisplay;
  }

  public static void fetchReservedChars( String urlPart, String username, String password ) {

    if (!urlPart.endsWith("/")) {
      urlPart += "/";
    }

    Client client = ClientBuilder.newClient();
    HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
    client.register(feature);

    try{

      String url = urlPart.concat( RESERVED_CHARS_URL );
      WebTarget resource = client.target(url);
      Response response = resource.request().get();
      setReservedChars( response.readEntity( String.class ) );

      url = urlPart.concat( RESERVED_CHARS_URL_DISPLAY );
      resource = client.target(url);
      response = resource.request().get();
      setReservedCharsDisplay( response.readEntity( String.class ) );

    }catch(Exception e){
      LOG.warning( "Reserved character call failed: " + e.getMessage());
      // ignored intentionally; will fall back to defaults
    }
    finally {
      client.close();
    }

  }

}
