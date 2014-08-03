package com.mycompany.hr.ws;
        
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.Namespace;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;
import org.springframework.xml.xpath.XPathExpressionFactory;
import org.w3c.dom.Node;

import com.mycompany.hr.schemas.HolidayRequest;
import com.mycompany.hr.schemas.HolidayResponse;
import com.mycompany.hr.service.HumanResourceService;


@Endpoint
public class HolidayEndpoint {

private static final String NAMESPACE_URI = "http://mycompany.com/hr/schemas";
                                             
@Autowired 
private HumanResourceService humanResourceService;

@Autowired 
private XmlTools xmlTools;

@Resource(name="xsdNamespaces")
private Map<String,String> xsdNamespaces;


@Autowired
private org.springframework.xml.xpath.XPathExpression holidayRequestFirstNameExpression;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "HolidayRequest")
	@ResponsePayload
	@Namespace(prefix = "hr", uri=NAMESPACE_URI)
	public HolidayResponse handleHolidayRequest(@RequestPayload HolidayRequest holidayRequest,
			@XPathParam("/hr:HolidayRequest/hr:Employee/hr:LastName") String lastName,
			@XPathParam("/hr:HolidayRequest") Node holidatRequestNode) throws Exception {
		
		// access via JAXB2 bean
		Date start = xmlTools.asDate(holidayRequest.getHoliday().getStartDate());
	    Date end =  xmlTools.asDate(holidayRequest.getHoliday().getEndDate());
	    String name = holidayRequest.getEmployee().getFirstName();
	    
	    name += "(";

		// access lastName vie XPathParam
	    name+="lastName = "+lastName;
	    
//	    manual Xpath traversing of Node object..
	    String customerId = XPathExpressionFactory.createXPathExpression("/hr:HolidayRequest/hr:Employee/hr:Number", xsdNamespaces).evaluateAsString(holidatRequestNode);
	    name+=", customerId = "+customerId;
	    
	    name += ")";

	    // call the "business logic"
	    humanResourceService.bookHoliday(start, end, name);

	    // return response
	    HolidayResponse res = new HolidayResponse();
	    res.setResponseCode("OK - response comming from HolidayEndpoint. Values received:" +name);
	    return res;
	}

	public HumanResourceService getHumanResourceService() {
		return humanResourceService;
	}


	public void setHumanResourceService(HumanResourceService humanResourceService) {
		this.humanResourceService = humanResourceService;
	}

	
}