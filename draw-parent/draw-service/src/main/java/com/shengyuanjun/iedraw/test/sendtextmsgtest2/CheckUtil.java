package com.shengyuanjun.iedraw.test.sendtextmsgtest2;

import java.security.MessageDigest;
import java.util.Arrays;

public class CheckUtil {
	//与接口配置信息中的Token要一致 
	private static final String token="weixinStudy";
	
	public static boolean checkSignature(String signature,String timestrap,
			String nonce){
		
		String[] arr=new String[]{token,timestrap,nonce};
		// 将token、timestamp、nonce三个参数进行字典序排序 
		Arrays.sort(arr);
		
		StringBuffer buf=new StringBuffer();
		for(int i=0;i<arr.length;i++){
			buf.append(arr[i]);
		}
		
		String temp=getSha1(buf.toString());
		return temp.equals(signature);
	}
	
	public static String getSha1(String str){
		if(null==str || str.length()==0){
			return null;
		}
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9',
				'a','b','c','d','e','f'};
		try{
			MessageDigest mdTemp=MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			
			byte[] md=mdTemp.digest();
			int j=md.length;
			char[] buf=new char[j*2];
			int k=0;
			for(int i=0;i<j;i++){
				byte byTemp=md[i];
				buf[k++]=hexDigits[byTemp >>> 4 & 0xf];
				buf[k++]=hexDigits[byTemp & 0xf];
			}
			return new String(buf);
		}catch(Exception e){
			return null;
		}
	}

}