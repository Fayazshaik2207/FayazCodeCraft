package in.sf.main.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import in.sf.main.entities.Employee;
import in.sf.main.entities.FollowUps;
import in.sf.main.entities.Inquiry;
import in.sf.main.services.FollowUpsService;
import in.sf.main.services.InquiryService;

@Controller
@SessionAttributes("sessionEmp")
public class InquiryController {
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private FollowUpsService followUpsService;
	
	@GetMapping("/newInquiry")
	public String openNewInqueryPage(Model model) {
		model.addAttribute("inquiry",new Inquiry());
		return "new-inquiry";
	}
	
	@PostMapping("/submitInquiryForm")
	public String submitInquiryForm(@ModelAttribute("inquiry") Inquiry inquiry,
			@SessionAttribute("sessionEmp") Employee employee,Model model,
			@RequestParam(name="followUpDate",required=false) String followUpDate,
			@RequestParam(name="sourcePage",required = false) String sourcePage) {
		
		inquiry.setEmpEmail(employee.getEmail());
		
		LocalDate dt = LocalDate.now();
		String date = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		LocalTime lt = LocalTime.now();
		String time = lt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		
		inquiry.setDateOfInquiry(date);
		inquiry.setTimeOfInquiry(time);
		
		try {
			
			inquiryService.addNewInquiry(inquiry);
			model.addAttribute("SuccessMsg","New Inquiry added Successfully!!");
			if(inquiry.getStatus().equals("Interested - (follow up)") && followUpDate != null) {
				FollowUps followUps = new FollowUps();
				followUps.setPhoneno(inquiry.getPhoneno());
				followUps.setEmpEmail(employee.getEmail());
				followUps.setFollowUpDate(followUpDate);
				followUpsService.addFollowUpsDate(followUps);
			}
			else {
				followUpsService.deleteByPhoneNumber(inquiry.getPhoneno());
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("ErrorMsg","some error occured,try again !!");
		}
		if("followUpPage".equals(sourcePage)) {
			model.addAttribute("SuccessMsg","Inquiry handled Successfully!!");
			return "follow-ups";
		}
		else {
			return "inquiry-management";
		}
		
	}
}
