package jp.crudefox.server.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

public class CFUtil {





	public static byte[] toBytesFtomInputStream(InputStream is){
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len = 0;
		try {
			while((len=is.read(buf))!=-1){
				ba.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return ba.toByteArray();
	}

	public static String toBase64FromBytes(byte[] data){
		if(data==null) return null;
		return new String( Base64.encodeBase64(data, false, false) );
	}

	public static byte[] toBytesFromBase64(String data){
		if(data==null) return null;
		return Base64.decodeBase64(data);
	}


	public static String convertCalendar2MySQLDATETIMEString(Calendar cal)
	{
		if(cal == null)
		{
			return "1000-01-01 00:00:00";
		}

		DecimalFormat int2df = new DecimalFormat("00");
		StringBuffer sb = new StringBuffer();
		sb.append(cal.get(Calendar.YEAR));
		sb.append("-");
		sb.append(int2df.format(cal.get(Calendar.MONTH) + 1));
		sb.append("-");
		sb.append(int2df.format(cal.get(Calendar.DAY_OF_MONTH)));
		sb.append(" ");
		sb.append(int2df.format(cal.get(Calendar.HOUR_OF_DAY)));
		sb.append(":");
		sb.append(int2df.format(cal.get(Calendar.MINUTE)));
		sb.append(":");
		sb.append(int2df.format(cal.get(Calendar.SECOND)));

		return sb.toString();
	}

	private static final SimpleDateFormat sDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static final Date parseDateTme(String str){
		try {
			return sDateTimeFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static final Date parseDate(String str){
		try {
			return sDateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final String toDateString(Date date){
		try {
			return sDateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static final String toDateString(long date){
		return toDateString(new java.util.Date(date));
	}

	public static final String toDateTimeString(long date){
		return toDateTimeString(new java.util.Date(date));
	}
	public static final String toDateTimeString(Date date){
		try {
			return sDateTimeFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static final void initMySQLDriver(){
		try {
			  Class.forName("com.mysql.jdbc.Driver").newInstance();
			  //com.mysql.jdbc.Driver d = new com.mysql.jdbc.Driver();

		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static String getParam(HttpServletRequest request, String key){
		String val = request.getParameter(key);
		String val2 = decodeStringFrom8859_1(val);
		return val2;
	}
	public static String[] getParamValues(HttpServletRequest request, String key){
		String[] vals = request.getParameterValues(key);
		if(vals==null) return null;
		String[] vals2 = new String[vals.length];
		for(int i=0;i<vals.length;i++){
			vals2[i] = decodeStringFrom8859_1(vals[i]);
		}
		return vals2;
	}

	public static String decodeStringFrom8859_1(String str){
		return decodeStringFrom(str, "ISO-8859-1");
	}

	public static String decodeStringFrom(String str, String encode){
	    try {
	      byte[] byteData = str.getBytes(encode);
	      str = new String(byteData, "UTF-8");
	    }catch(Exception e){
	      return null;
	    }

	    return str;
	}







}


