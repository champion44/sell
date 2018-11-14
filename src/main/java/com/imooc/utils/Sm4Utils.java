package com.imooc.utils;

public class Sm4Utils {
	
/*	private static final byte[] key = {(byte)0x31,(byte)0x35,(byte)0x33,(byte)0x32,(byte)0x38,(byte)0x35,(byte)0x30,(byte)0x31,(byte)0x31,(byte)0x30,(byte)0x30,(byte)0x68,(byte)0x79,(byte)0x73,(byte)0x67,(byte)0x30};
*/	
	public String StringToSm4(String str,String key){
		SMS4 sm4 = new SMS4();
		int inLen = 16, ENCRYPT = 1;
		byte[] out = new byte[16];
		byte[] in = new byte[16];
		
		String key1 = key+"hysg0";
		
		byte[] mkey = key1.getBytes();
		
		byte[] in2 = str.getBytes();
        System.arraycopy(in2,0,in,0,in2.length);
        
        StringBuffer result = new StringBuffer("");
        
        sm4.sms4(in, inLen, mkey, out, ENCRYPT);
        
        for (int i = 0; i < out.length; i++) {
			String hexString = Integer.toHexString(out[i]);   
			int outlen =  hexString.length();
			result.append(hexString.substring(outlen-2));
        }
        
		return result.toString();
		
	}
	
	public String Sm4ToString(String str,String key){
    	SMS4 sm4 = new SMS4();
    	int inLen=16,DECRYPT=0;
		byte[] out = new byte[16];
		byte[] in = new byte[16];
		
        String key1 = key+"hysg0";
		
		byte[] mkey = key1.getBytes();
		
		StringBuffer result = new StringBuffer();
		
		StringBuffer s1 = new StringBuffer(str);
        int index;
        for (index = 2; index < s1.length(); index += 3) {
            s1.insert(index, ',');
        }
        String[] array = s1.toString().split(",");
        
        for (int i = 0; i < array.length; i++) {
        	out[i]=(byte)Integer.parseInt(array[i],16);
		}
        
		sm4.sms4(out, inLen, mkey, in, DECRYPT);
		
		 for (int i = 0; i < in.length-5; i++) {
				String hexString = Integer.toHexString(in[i]);   
				int inlen =  hexString.length();
				result.append(hexString.substring(inlen-1));
	        }
		return result.toString();
	}
	

}
