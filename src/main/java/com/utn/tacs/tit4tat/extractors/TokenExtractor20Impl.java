package com.utn.tacs.tit4tat.extractors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.utn.tacs.tit4tat.exceptions.OAuthException;
import com.utn.tacs.tit4tat.model.Token;
import com.utn.tacs.tit4tat.utils.OAuthEncoder;
import com.utn.tacs.tit4tat.utils.Preconditions;

/**
 * Default implementation of {@link AccessTokenExtractor}. Conforms to OAuth 2.0
 */
public class TokenExtractor20Impl implements AccessTokenExtractor
{
  private static final String TOKEN_REGEX = "access_token=([^&]+)";
  private static final String EMPTY_SECRET = "";

  /**
   * {@inheritDoc} 
   */
  public Token extract(String response)
  {
    Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");

    Matcher matcher = Pattern.compile(TOKEN_REGEX).matcher(response);
    if (matcher.find())
    {
      String token = OAuthEncoder.decode(matcher.group(1));
      return new Token(token, EMPTY_SECRET, response);
    } 
    else
    {
      throw new OAuthException("Response body is incorrect. Can't extract a token from this: '" + response + "'", null);
    }
  }
}
