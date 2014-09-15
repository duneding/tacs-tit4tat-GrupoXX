package com.utn.tacs.tit4tat.services;

import javax.xml.bind.*;

public class DatatypeConverterEncoder extends Base64Encoder
{
  @Override
  public String encode(byte[] bytes)
  {
    return DatatypeConverter.printBase64Binary(bytes);
  }

  @Override
  public String getType()
  {
    return "DatatypeConverter";
  }
}
