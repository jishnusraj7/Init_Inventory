package com.indocosmo.mrp.utils.core;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;



public class PosImageUtils {

	
	/**
	 * @param imagePath
	 * @return
	 * @throws Exception
	 */
	public static String encodeToBase64(String imagePath) throws Exception{
		
		final File file = new File(imagePath);
		final FileInputStream imageInFile = new FileInputStream(file);
        
		byte imageData[] = new byte[(int) file.length()];
        imageInFile.read(imageData);
        imageInFile.close();
        
        return Base64.encodeBase64URLSafeString(imageData);
	}
	
	
	/**
	 * decodes an image from base64 format.
	 * @param imgString
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage decodeFromBase64(String imgString) throws Exception{
		
		byte[] imagebyte;
		imagebyte = Base64.decodeBase64(imgString);
		ByteArrayInputStream bin = new ByteArrayInputStream(imagebyte);
		final BufferedImage image=ImageIO.read(bin);
		bin.close();
		return image;
		
	}
	
	

	/**
	 * encodes an image to base64 format
	 * @param image
	 * @param type
	 * @return
	 * @throws IOException
	 */
//	public static String encodeToBase64(BufferedImage image,String type) throws IOException{
//		BufferedImage iImage =image;
//		String base64String=null;
//		ByteArrayOutputStream bout = new ByteArrayOutputStream();
//		ImageIO.write(iImage,type, bout);
//		BASE64Encoder encoder = new BASE64Encoder();
//		base64String = encoder.encode(bout.toByteArray());
//		bout.close();
//		return base64String;
//	}

	
	/**
	 * decodes an image from base64 format.
	 * @param imgString
	 * @return
	 * @throws IOException
	 */
//	public static BufferedImage decodeFromBase64(String imgString) throws IOException{
//		
//		
//		String base64String = imgString;
//		BufferedImage image;
//		byte[] imagebyte;
//		BASE64Decoder decoder = new BASE64Decoder();
//		imagebyte = decoder.decodeBuffer(base64String);
//		ByteArrayInputStream bin = new ByteArrayInputStream(imagebyte);
//		image=ImageIO.read(bin);
//		bin.close();
//		return image;
//	}
}
