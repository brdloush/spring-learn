package com.mycompany.hr.client;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.mycompany.hr.schemas.Employee;
import com.mycompany.hr.schemas.Holiday;
import com.mycompany.hr.schemas.HolidayRequest;
import com.mycompany.hr.schemas.HolidayResponse;

@Component("hrWsClient")
public class HrWsClientImpl implements HrWsClient{

	@Resource(name="ws.endpoint.uri.holidayRequest")
	String wsEndpointUri;
	
	
	@Resource(name="webServiceWithJaxb2MarshallerTemplate")
	WebServiceTemplate webServiceTemplate;

	private XMLGregorianCalendar dateToXmlGregorianCalendar(Date d) {
		if (d == null) {
			return null;
		}
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);			
		} catch (DatatypeConfigurationException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public HolidayResponse holidayRequest(String firstName, String lastName, Date dateFrom, Date dateTo) {

		HolidayRequest req = new HolidayRequest();
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		req.setEmployee(employee);
		Holiday holiday = new Holiday();		
		holiday.setStartDate(dateToXmlGregorianCalendar(dateFrom) );
		holiday.setEndDate(dateToXmlGregorianCalendar(dateTo) );
		req.setHoliday(holiday);
		
		HolidayResponse result = (HolidayResponse) webServiceTemplate.marshalSendAndReceive(wsEndpointUri,req);		
		return result;
	}

	
	
}
