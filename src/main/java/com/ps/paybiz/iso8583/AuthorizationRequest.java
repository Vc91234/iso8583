package com.ps.paybiz.iso8583;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.ISODate;

public class AuthorizationRequest {
	public static void main(String[] args) throws ISOException, IOException {

		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(2, "63442866726"); // Primary Account Number
		map.put(3, "501010"); // Processing Code
		map.put(4, "10000"); // Transaction Amount
		map.put(7, ISODate.getDateTime(new Date(), TimeZone.getTimeZone("IST"))); // MMDDHHMMSS
		map.put(11, "123456"); // System Trace Audit Number
//		map.put(42, "111111"); // Card Acceptor ID Code(Merchant ID)

//		System.out.println("ISO Message :" + build(map, "0200"));

//		parse(build(map,"0100"));
		
		parse("021072200000020000001163442866726501010000000010000121217350412345600");
	}

	public static String build(Map<Integer, String> input, String Mti) throws IOException, ISOException {

		GenericPackager packager = new GenericPackager("src/main/resources/basic.xml");
		Logger logger = Logger.getLogger("log");
		logger.setLevel(Level.INFO);

		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.setMTI(Mti);

		Iterator<Map.Entry<Integer, String>> itr = input.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Integer, String> entry = itr.next();
			isoMsg.set(entry.getKey(), entry.getValue());
		}

		byte[] result = isoMsg.pack();

		return new String(result);
	}

	public static void parse(String message) throws IOException, ISOException {


		GenericPackager packager = new GenericPackager("src/main/resources/basic.xml");
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.unpack(message.getBytes());

		System.out.println("MTI - "+isoMsg.getMTI());

//		System.out.println(isoMsg.getMaxField());

		for (int i=1;i<=isoMsg.getMaxField();i++) {
			if (isoMsg.hasField(i)) {
				System.out.println("Field-"+i+" : "+isoMsg.getString(i));
			}
		}

	}

}
