package com.utn.tacs.extractors;

import java.util.regex.*;

import com.utn.tacs.exceptions.*;
import com.utn.tacs.model.*;
import com.utn.tacs.utils.*;

public class JsonTokenExtractor implements AccessTokenExtractor
{
  private Pattern accessTokenPattern = Pattern.compile("\"access_token\":\\s*\"(\\S*?)\"");

  public Token extract(String response)
  {
    Preconditions.checkEmptyString(response, "Cannot extract a token from a null or empty String");
    Matcher matcher = accessTokenPattern.matcher(response);
    if(matcher.find())
    {
      return new Token(matcher.group(1), "", response);
    }
    else
    {
      throw new OAuthException("Cannot extract an access token. Response was: " + response);
    }
  }

}