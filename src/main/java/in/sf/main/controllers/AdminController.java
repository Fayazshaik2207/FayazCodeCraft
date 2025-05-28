package in.sf.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sf.main.services.FeedbackService;
import in.sf.main.entities.*;

@Controller
@SessionAttributes("sessionAdm")
public class AdminController {
	
//	-----------------admin login starts-------------------
	@GetMapping("/adminLogin")
	public String openAdminLoginPage() {
		return "admin-login";
	}
	
	@PostMapping("/adminLoginForm")
	public String loginAdminPage(@RequestParam("adminemail") String email,@RequestParam("adminpassword") String password,Model model ) {
		if(email.equals("admin@gmail.com") && password.equals("admin123")) {
			return "redirect:/adminProfile";
		}
		else {
			model.addAttribute("ErrorMsg","Invalid email or password");
			return "admin-login";
		}
	}
	
	@GetMapping("/adminLogout")
	public String employeeLogout(SessionStatus sessionStatus)
	{
		sessionStatus.setComplete(); 
		return "admin-login";
	}
//	-----------------admin login ends-------------------
	
	
//	-----------------------feedback-----------------------
	@Autowired
	private FeedbackService feedbackService;
	@GetMapping("/adminFeedback")
	public String openFeedbackPage(Model model,
			@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size", defaultValue = "4") int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Feedback> feedbackPage = feedbackService.getAllFeedbacksByPagination(pageable);
		
		model.addAttribute("feedbackPage", feedbackPage);
		
		return "view-feedbacks";
	}
	
    @PostMapping("/updateFeedbackStatus")
    public String updateFeedbackStatus(@RequestParam("id") Long id, @RequestParam("status") String status, RedirectAttributes redirectAttributes)
    {
        boolean success = feedbackService.updateFeedbackStatus(id, status);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "Feedback updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Failed to update feedback status.");
        }
        return "redirect:/adminFeedback"; // Redirect to the page where feedbacks are listed
    }
	
}