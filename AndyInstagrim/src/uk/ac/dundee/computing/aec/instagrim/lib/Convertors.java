package uk.ac.dundee.computing.aec.instagrim.lib;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.StringTokenizer;





import javax.imageio.ImageIO;
// import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

public final class Convertors {
	
    public static int DISPLAY_IMAGE=0;
    public static int DISPLAY_THUMB=1;
    public static int DISPLAY_PROCESSED=2;
    
    // Add 23/10 for filter
   // public static int DISPLAY_FILTER=3;
    
    public void Convertors() {

    }

    public static java.util.UUID getTimeUUID() {
    	
        return java.util.UUID.fromString(new com.eaio.uuid.UUID().toString());
        
    }
    
    

    public static byte[] asByteArray(java.util.UUID uuid) {

        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
        	
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
            
        }
        
        for (int i = 8; i < 16; i++) {
        	
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
            
        }

        return buffer;
    }

    public static byte[] longToByteArray(long value) {
    	
        byte[] buffer = new byte[8]; //longs are 8 bytes I believe
        
        for (int i = 7; i >= 0; i--) { //fill from the right
        	
            buffer[i] = (byte) (value & 0x00000000000000ff); //get the bottom byte

            //System.out.print(""+Integer.toHexString((int)buffer[i])+",");
            value = value >>> 8; //Shift the value right 8 bits
            
        }
        return buffer;
    }

    public static long byteArrayToLong(byte[] buffer) {
    	
        long value = 0;
        long multiplier = 1;
        
        for (int i = 7; i >= 0; i--) { //get from the right

            //System.out.println(Long.toHexString(multiplier)+"\t"+Integer.toHexString((int)buffer[i]));
            value = value + (buffer[i] & 0xff) * multiplier; // add the value * the hex mulitplier
            multiplier = multiplier << 8;
        }
        return value;
    }
    
    //********************************************************************
    
    // Add for filter 23/10 (with much help of StackOverflow to be honest)
    public static BufferedImage byteArrayToBufferedImage(byte[] arr){
    	
    	BufferedImage original = null;
    	
    	try{
    		
    		ByteArrayInputStream b = new ByteArrayInputStream(arr);
        	original = ImageIO.read(b);
        	b.close();
    		
    	}catch(IOException e){
    		
    		e.printStackTrace();
    		
    	}
    	
    	return original;
    }
    
    public static byte[] bufferedImageToByteArray(BufferedImage im, String formatString){
    	
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	byte[] imageByteArr = null;
    	
    	try{
    		
    		if(!ImageIO.write(im, formatString, bos)){
    			
    			System.out.println("Error in Image IO");
    			
    		}
    		imageByteArr = bos.toByteArray();
        	bos.close();
    		
    	}catch(IOException e){
    		
    		e.printStackTrace();
    		
    	}
    	
    	return imageByteArr;
    	
    }
    
    //********************************************************************

    public static void displayByteArrayAsHex(byte[] buffer) {
    	
        int byteArrayLength = buffer.length;
        for (int i = 0; i < byteArrayLength; i++) {
            int val = (int) buffer[i];
            // System.out.print(Integer.toHexString(val)+",");
        }

	  //System.out.println();
    }

//From: http://www.captain.at/howto-java-convert-binary-data.php
    public static long arr2long(byte[] arr, int start) {
    	
        int i = 0;
        int len = 4;
        int cnt = 0;
        byte[] tmp = new byte[len];
        
        for (i = start; i < (start + len); i++) {
            tmp[cnt] = arr[i];
            cnt++;
        }
        
        long accum = 0;
        i = 0;
        
        for (int shiftBy = 0; shiftBy < 32; shiftBy += 8) {
            accum |= ((long) (tmp[i] & 0xff)) << shiftBy;
            i++;
        }
        
        return accum;
    }

    public static String[] SplitTags(String Tags) {
    	
        String args[] = null;

        StringTokenizer st = Convertors.SplitTagString(Tags);
        args = new String[st.countTokens() + 1];  //+1 for _No_Tag_
        //Lets assume the number is the last argument

        int argv = 0;
        
        while (st.hasMoreTokens()) {;
            args[argv] = new String();
            args[argv] = st.nextToken();
            argv++;
        }
        
        args[argv] = "_No-Tag_";
        return args;
        
    }

    private static StringTokenizer SplitTagString(String str) {
    	
        return new StringTokenizer(str, ",");

    }

    public static String[] SplitFiletype(String type) {
    	
        String args[] = null;

        StringTokenizer st = SplitString(type);
        args = new String[st.countTokens()];
		//Lets assume the number is the last argument

        int argv = 0;
        while (st.hasMoreTokens()) {;
            args[argv] = new String();

            args[argv] = st.nextToken();
            try {
                //System.out.println("String was "+URLDecoder.decode(args[argv],"UTF-8"));
                args[argv] = URLDecoder.decode(args[argv], "UTF-8");

            } catch (Exception et) {
                System.out.println("Bad URL Encoding" + args[argv]);
            }
            argv++;
        }

	//so now they'll be in the args array.  
        // argv[0] should be the user directory
        return args;
        
    }
    
    public static String[] SplitRequestPath(HttpServletRequest request) {
    	
        String args[] = null;

        StringTokenizer st = SplitString(request.getRequestURI());
        args = new String[st.countTokens()];
		//Lets assume the number is the last argument

        int argv = 0;
        while (st.hasMoreTokens()) {;
            args[argv] = new String();

            args[argv] = st.nextToken();
            try {
                //System.out.println("String was "+URLDecoder.decode(args[argv],"UTF-8"));
                args[argv] = URLDecoder.decode(args[argv], "UTF-8");

            } catch (Exception et) {
                System.out.println("Bad URL Encoding" + args[argv]);
            }
            argv++;
        }

	//so now they'll be in the args array.  
        // argv[0] should be the user directory
        return args;
        
    }

    private static StringTokenizer SplitString(String str) {
    	
        return new StringTokenizer(str, "/");

    }

}
