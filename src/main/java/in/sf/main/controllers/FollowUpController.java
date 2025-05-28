package in.sf.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("sessionEmp")
public class FollowUpController {

	@GetMapping("/followUps")
	public String openFollowUpDatePage() {
		return "follow-ups";
	}
}
