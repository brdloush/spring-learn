package com.mycompany.hr.ws;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;


@Component
public class XmlToolsImpl implements XmlTools {

	public Date asDate(XMLGregorianCalendar xmlDate) {
		return xmlDate.toGregorianCalendar().getTime();
	}

	
}
