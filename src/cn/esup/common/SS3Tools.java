package cn.esup.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import org.apache.commons.lang.xwork.math.RandomUtils;

/**
 * 方便使用SpringSide3开发的一些小工具
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 */
public class SS3Tools {

	/** ids转换为长整形数组 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public static Long[] ids2LongArray(final String ids) {
		/** 如果字符串数组为空或者长度为0，则返回一个长度为0的长整形数组 */
		if (ids == null || ids.length() <= 0) {
			return new Long[0];
		} else {
			String[] tmp = ids.split(",");
			Long[] array = new Long[tmp.length];
			for (int i = 0; i < tmp.length; i++) {
				array[i] = Long.parseLong(tmp[i]);
			}
			return array;
		}
	}

	/** MD5加密 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	/** 获取字符串编码类型 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

	/** 生成18位随机流水号 */
	public static synchronized String genSerialNumber() {
		StringBuilder sb = new StringBuilder();
		sb.append(Calendar.getInstance().getTimeInMillis());
		sb.append(RandomUtils.nextInt(99999));
		return sb.toString();
	}

	/** 测试 */
	public static void main(String[] args) {
		//	Long[] test = SS3Tools.ids2LongArray("1,2");
		//	System.out.println(test.length + "," + test[0] + "," + test[1]);

		//	System.out.println(SS3Tools.getMD5Str("jay123"));// MD5

		System.out.println(genSerialNumber());
	}
}
