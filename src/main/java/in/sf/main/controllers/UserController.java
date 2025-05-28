package in.sf.main.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import in.sf.main.dto.PurchasedCourse;
import in.sf.main.entities.Course;
import in.sf.main.entities.User;
import in.sf.main.repositories.OrdersRepository;
import in.sf.main.repositories.UserRepository;
import in.sf.main.services.CourseService;
import in.sf.main.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("SessionUser")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrdersRepository ordersRepository;

	@GetMapping({ "/", "/index" })
	public String openIndexPage(Model model,
			@SessionAttribute(name = "SessionUser", required = false) User sessionUser) {

		List<Course> coursesList = courseService.getAllCourses();
		model.addAttribute("coursesList", coursesList);
		if (sessionUser != null) {
			List<Object[]> purchasedList = ordersRepository.findPurchasedCoursesByEmail(sessionUser.getEmail());
			List<String> purchasedNameList = new ArrayList<>();
			for (Object[] course : purchasedList) {
				String name = (String) course[1];
				purchasedNameList.add(name);
			}
			model.addAttribute("purchasedNameList", purchasedNameList);
			System.out.println(purchasedNameList);
		}
		return "index";

	}
//	-----------Login starts---------------------

	@GetMapping("/login")
	public String openLoginPage(Model model) {

		model.addAttribute("user", new User());
		return "login";

	}

	@PostMapping("/loginForm")
	public String handleLoginForm(@ModelAttribute("user") User user, Model model) {
		boolean status = userService.userLogin(user.getEmail(), user.getPassword());
		if (status) {
			User authenticatedUser = userRepository.findByEmail(user.getEmail());
			if (authenticatedUser.isBanStatus()) {
				model.addAttribute("ErrorMsg", "Sorry, your account is banned, please contact admin, thank you...!!");
				return "login";
			}
			model.addAttribute("SessionUser", authenticatedUser);
			return "user-profile";
		} else {
			model.addAttribute("ErrorMsg", "Incorrect Email or Password!!");
			return "/login";
		}
	}

//	@GetMapping("/logout")
//	public String userLogout(SessionStatus sessionStatus) {
//		sessionStatus.setComplete();
//		return "login";
//	}
	@GetMapping("/logout")
	public String userLogout(SessionStatus sessionStatus, HttpServletRequest request) {
		sessionStatus.setComplete(); // Clears @SessionAttributes
		request.getSession().invalidate(); // Invalidate HTTP session
		return "redirect:/login";
	}

//	-----------Login Finished---------------------

//	-----------registration starts---------------------
	@GetMapping("/register")
	public String openRegisterPage(Model model) {

		model.addAttribute("user", new User());
		return "register";

	}

	@PostMapping("/regForm")
	public String handleRegForm(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "register";
		} else {
			try {
				userService.userRegistration(user);
				model.addAttribute("SuccessMsg", "Registered Successfully!!");
				return "register";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("ErrorMsg", "Registration Failed!!");
				return "error";
			}
		}
	}
//	-----------registration Ends---------------------

	@GetMapping("/user-enrollments")
	public String openUserEnrollments(@SessionAttribute("SessionUser") User sessionUser, Model model) {
		List<Object[]> pcDbList = ordersRepository.findPurchasedCoursesByEmail(sessionUser.getEmail());
		List<PurchasedCourse> purchasedCourseList = new ArrayList<>();
		for (Object[] course : pcDbList) {
			PurchasedCourse purchasedCourse = new PurchasedCourse();
			purchasedCourse.setPurchasedOn((String) course[0]);
			purchasedCourse.setName((String) course[1]);
			purchasedCourse.setImageUrl((String) course[2]);
			purchasedCourse.setDescription((String) course[3]);
			purchasedCourse.setUpdatedOn((String) course[4]);
			purchasedCourseList.add(purchasedCourse);
		}
		model.addAttribute("purchasedCourseList", purchasedCourseList);
		return "user-enrollments";
	}

	@GetMapping("/user-profile")
	public String openUserProfile() {
		return "user-profile";
	}

}
