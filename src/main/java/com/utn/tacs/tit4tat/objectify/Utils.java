package com.utn.tacs.tit4tat.objectify;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.google.appengine.api.datastore.Blob;
import com.utn.tacs.tit4tat.model.Usuario;

public class Utils {
	
	public static Blob getImageAsBlob(String thumbnail){
		ByteArrayOutputStream bis = new ByteArrayOutputStream();
		InputStream is = null;
		Blob result = null;
		try {
			URL url = new URL(thumbnail);
			is = url.openStream ();
			byte[] bytebuff = new byte[4096]; 
			int n;
			result = new Blob(IOUtils.toByteArray(is));
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
				
		return result;		
	}
	
	public static Usuario generateUser(String userValue){
		
		String[] splitValues = userValue.substring(9).replace("]", "").split(",");
		Long id =Long.valueOf(splitValues[0].split("=")[1]);
		String name = splitValues[1].split("=")[1].trim();		
		Usuario user = new Usuario(name);
		user.setId(id);
		return user;
	}

}
