package in.sf.main.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sf.main.entities.Inquiry;
import in.sf.main.services.InquiryService;

@RestController 
@RequestMapping("/api")
public class InquiryApi {
	
	@Autowired
	private InquiryService inquiryService;
	
	@GetMapping("/searchInquiries")
	public List<Inquiry> searchInquiries(String phoneNumber) {
		return  inquiryService.searchInquiriesByPhoneno(phoneNumber);
	}
}
