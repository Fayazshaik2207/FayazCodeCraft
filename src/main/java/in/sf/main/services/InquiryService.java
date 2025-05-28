package in.sf.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sf.main.entities.Inquiry;
import in.sf.main.repositories.InquiryRepository;

@Service
public class InquiryService {
	
	@Autowired
	private InquiryRepository inquiryRepository;
	
	public void addNewInquiry(Inquiry inquiry) {
		inquiryRepository.save(inquiry);
	}
	
	public List<Inquiry> searchInquiriesByPhoneno(String phoneno) {
		return inquiryRepository.findByPhoneno(phoneno);
	}
}
