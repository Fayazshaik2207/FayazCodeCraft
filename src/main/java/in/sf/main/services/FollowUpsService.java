package in.sf.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sf.main.entities.FollowUps;
import in.sf.main.repositories.FollowUpsRepository;

@Service
public class FollowUpsService {

	@Autowired
	private FollowUpsRepository followUpsRepository;
	
	public void addFollowUpsDate(FollowUps followUps) {
		Optional<FollowUps> optional = followUpsRepository.findByPhoneno(followUps.getPhoneno());
		if(optional.isPresent()) {
			FollowUps oldFollowUps = optional.get();
			oldFollowUps.setFollowUpDate(followUps.getFollowUpDate());
			followUpsRepository.save(oldFollowUps);
		}
		else {
			followUpsRepository.save(followUps);
		}
	}
	
	public List<FollowUps> getFolloUps(String empEmail,String followUpDate) {
		return followUpsRepository.findByEmpEmailAndFollowUpDate(empEmail, followUpDate);
	}
	
	public void deleteByPhoneNumber(String phoneno) {
		Optional<FollowUps>  optional = followUpsRepository.findByPhoneno(phoneno);
		if(optional.isPresent()) {
			FollowUps followups = optional.get();
			followUpsRepository.delete(followups);
		}
	}
}
