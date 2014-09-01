package com.utn.tacs.services;

import com.utn.tacs.exceptions.*;
import com.utn.tacs.utils.*;

/**
 * plaintext implementation of {@link SignatureService}
 *
 * @author Pablo Fernandez
 *
 */
public class PlaintextSignatureService implements SignatureService
{
  private static final String METHOD = "PLAINTEXT";

  /**
   * {@inheritDoc}
   */
  public String getSignature(String baseString, String apiSecret, String tokenSecret)
  {
    try
    {
      Preconditions.checkEmptyString(apiSecret, "Api secret cant be null or empty string");
      return OAuthEncoder.encode(apiSecret) + '&' + OAuthEncoder.encode(tokenSecret);
    }
    catch (Exception e)
    {
      throw new OAuthSignatureException(baseString, e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getSignatureMethod()
  {
    return METHOD;
  }
}

