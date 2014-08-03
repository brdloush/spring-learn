package com.mycompany.hr.client;

import java.util.Date;

import com.mycompany.hr.schemas.HolidayResponse;

public interface HrWsClient {

	HolidayResponse holidayRequest(String firstName, String lastName, Date dateFrom, Date dateTo);
	
}
