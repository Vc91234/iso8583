package com.ps.paybiz.iso8583;

import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

public class AuthorizationResponse {

	public static void main(String args[]) throws IOException,ISOException{
		
		build("0200722000000000000011634428667265010100000000100001212173504123456");
	}
	
	public static void build(String message) throws IOException,ISOException{
		GenericPackager packager = new GenericPackager("src/main/resources/basic.xml");
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.unpack(message.getBytes());
		isoMsg.setMTI("0210");
		isoMsg.set(39,"00");
		
		byte[] result = isoMsg.pack();
		
		System.out.println(new String(result));
	}
}
