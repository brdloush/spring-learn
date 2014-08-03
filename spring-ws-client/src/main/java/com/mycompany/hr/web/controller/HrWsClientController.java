package com.mycompany.hr.web.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.icu.text.SimpleDateFormat;
import com.mycompany.hr.client.HrWsClient;
import com.mycompany.hr.schemas.HolidayResponse;

@Controller
@RequestMapping("/clientTest")
public class HrWsClientController {

	@Autowired
	private HrWsClient hrWsClient;
	
	public static class WsClientTestForm {
		private String firstName;
		private String lastName;
		private String fromDate;
		private String toDate;
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getFromDate() {
			return fromDate;
		}
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		public String getToDate() {
			return toDate;
		}
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String view(ModelMap model) {
		return "/clientTest";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String submit(@ModelAttribute("wsClientTestForm") WsClientTestForm fr, ModelMap model) {		

		HolidayResponse response = hrWsClient.holidayRequest(fr.firstName, fr.lastName, asDate(fr.fromDate), asDate(fr.toDate));
		model.put("wsCallResult", response.getResponseCode());
		return "/clientTest";
	}

	private Date asDate(String stringDate) {
		if (stringDate == null) {
			return null;
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sd.parse(stringDate);
		} catch (ParseException e) {
			throw new IllegalArgumentException();
		}
	}

	
}
