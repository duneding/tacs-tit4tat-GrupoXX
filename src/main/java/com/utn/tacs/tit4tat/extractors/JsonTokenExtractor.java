package com.utn.tacs.tit4tat.extractors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.utn.tacs.tit4tat.exceptions.OAuthException;
import com.utn.tacs.tit4tat.model.Token;
import com.utn.tacs.tit4tat.utils.Preconditions;

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