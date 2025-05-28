package in.sf.main.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.sf.main.entities.FollowUps;
import in.sf.main.services.FollowUpsService;

@RestController
@RequestMapping("/api")
public class FollowUpApi {
	
	@Autowired
	private FollowUpsService followUpsService;

	@GetMapping("/myFollowUps")
	public ResponseEntity<List<FollowUps>> getFollowUpCustomers(@RequestParam("empEmail") String empEmail,@RequestParam("followUpdate") String followUpDate) {
		List<FollowUps> followUpsList = followUpsService.getFolloUps(empEmail, followUpDate);
		return ResponseEntity.ok(followUpsList);
	}
}
