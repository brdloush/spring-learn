package com.mycompany.hr.ws;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

public interface XmlTools {

	Date asDate(XMLGregorianCalendar xmlDate); 

}
