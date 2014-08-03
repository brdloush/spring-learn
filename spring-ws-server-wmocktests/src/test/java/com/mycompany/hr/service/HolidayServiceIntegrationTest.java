package com.mycompany.hr.service;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import com.mycompany.hr.ws.HolidayEndpoint;

@RunWith(SpringJUnit4ClassRunner.class)                                                 
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-ws-servlet.xml")  

public class HolidayServiceIntegrationTest {

  @Autowired
  private ApplicationContext applicationContext;                                        

  private MockWebServiceClient mockClient;

  @Before
  public void createClient() {
    mockClient = MockWebServiceClient.createClient(applicationContext);                 
  }
  
  @Mock
  private HumanResourceService humanResourceService;
   
  @InjectMocks
  @Autowired
  private HolidayEndpoint holidayEndpoint;
   
   
  @Before
  public void setUp() {
      MockitoAnnotations.initMocks(this);
  }
    
  @Test
  public void customerEndpoint() throws Exception {
    StringSource requestPayload = new StringSource(
      "      <hr:HolidayRequest xmlns:hr='http://mycompany.com/hr/schemas'> "
      + "         <hr:Holiday> "
      + "            <hr:StartDate>2013-07-31</hr:StartDate> "
      + "            <hr:EndDate>2013-08-05</hr:EndDate>"
      + "         </hr:Holiday>"
      + "         <hr:Employee>"
      + "            <hr:Number>42</hr:Number>"
      + "            <hr:FirstName>Tomas</hr:FirstName>"
      + "            <hr:LastName>Brejla</hr:LastName>"
      + "         </hr:Employee>"
      + "      </hr:HolidayRequest>");
    StringSource responsePayload = new StringSource(
      "<ns2:HolidayResponse xmlns:ns2='http://mycompany.com/hr/schemas'>"
      + "  <ns2:responseCode>OK - response comming from HolidayEndpoint. Values received:Tomas(lastName = Brejla, customerId = 42)</ns2:responseCode>"
      + "</ns2:HolidayResponse>");

    
    mockClient.sendRequest(withPayload(requestPayload)).                                
      andExpect(payload(responsePayload));                                              

    // expect call to business logic..
    verify(humanResourceService).bookHoliday(any(Date.class),any(Date.class),eq("Tomas(lastName = Brejla, customerId = 42)"));
  }


}