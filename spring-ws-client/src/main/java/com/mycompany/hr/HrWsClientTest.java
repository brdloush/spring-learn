package com.mycompany.hr;

import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import java.util.Date;

import javax.annotation.Resource;
import javax.xml.transform.Source;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;

import com.ibm.icu.text.SimpleDateFormat;
import com.mycompany.hr.client.HrWsClient;
import com.mycompany.hr.schemas.HolidayResponse;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/context-ws.xml")
public class HrWsClientTest {

	@Autowired
	private HrWsClient client;

	@Resource(name = "webServiceWithJaxb2MarshallerTemplate")
	private WebServiceTemplate webServiceTemplate;

	private MockWebServiceServer mockServer;

	@Before
	public void createServer() throws Exception {
		mockServer = MockWebServiceServer.createServer(webServiceTemplate);
	}

	@Test
	public void customerClient() throws Exception {
		Source requestPayload = new StringSource(
				"<ns2:HolidayRequest xmlns:ns2='http://mycompany.com/hr/schemas'> "
						+ "  <ns2:Holiday>"
						+ "    <ns2:StartDate>2014-08-04+02:00</ns2:StartDate> "
						+ "    <ns2:EndDate>2014-08-10+02:00</ns2:EndDate>"
						+ "  </ns2:Holiday>" + "  <ns2:Employee>"
						+ "    <ns2:Number>0</ns2:Number>"
						+ "    <ns2:FirstName>tomas</ns2:FirstName>"
						+ "    <ns2:LastName>brejla</ns2:LastName>"
						+ "  </ns2:Employee>" + 
						"</ns2:HolidayRequest>");

		Source responsePayload = new StringSource(
				"<ns2:HolidayResponse xmlns:ns2='http://mycompany.com/hr/schemas'> "
						+ "  <ns2:responseCode>ok!</ns2:responseCode>"
						+"</ns2:HolidayResponse>");

		mockServer.expect(payload(requestPayload)).andRespond(withPayload(responsePayload));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String firstName = "tomas";
		String lastName = "brejla";
		Date from = sdf.parse("2014-08-04");
		Date to = sdf.parse("2014-08-10");

		HolidayResponse holidayResponse = client.holidayRequest(firstName, lastName, from, to);

		mockServer.verify();
		assertEquals(holidayResponse.getResponseCode(), "ok!");
	}

}