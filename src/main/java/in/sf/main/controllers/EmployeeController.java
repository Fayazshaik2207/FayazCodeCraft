package in.sf.main.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sf.main.entities.Employee;
import in.sf.main.entities.EmployeeOrders;
import in.sf.main.entities.Inquiry;
import in.sf.main.entities.Orders;
import in.sf.main.repositories.EmployeeOrdersRepository;
import in.sf.main.repositories.EmployeeRepository;
import in.sf.main.services.CourseService;
import in.sf.main.services.EmployeeService;
import in.sf.main.services.OrdersService;

@Controller
@SessionAttributes("sessionEmp")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private EmployeeOrdersRepository employeeOrdersRepository;
	
//	-----------------Employee login starts-------------------
	@GetMapping("/employeeLogin")
	public String openEmployeeLoginPage() {
		return "employee-login";
	}
	
	@PostMapping("/employeeLoginForm")
	public String employeeLoginForm(@RequestParam("email") String email, @RequestParam("password") String pass, Model model)
	{
		boolean isAuthenticated = employeeService.loginEmpService(email, pass);
		if(isAuthenticated)
		{
			Employee authenticatedEmp = employeeRepository.findByEmail(email);
			model.addAttribute("sessionEmp", authenticatedEmp);
			
			return "employee-profile";
		}
		else
		{
			model.addAttribute("errorMsg", "Incorrect Email id or Password");
			return "employee-login";
		}
	}
	
		
//	-----------------Employee login ends-------------------
	
	
	
//	----------------Employee sideBar starts ---------------------
	
		//-------------profile page------------------------
		@GetMapping("/employeeProfile")
		public String openEmployeeProfilePage() {
			return "employee-profile";
		}
	
		//-------------sell course------------------------
		@GetMapping("/sellCourse")
		public String openSellCoursePage(SessionStatus sessionStatus,Model model)
		{
			List<String> courseNamesList = courseService.getAllCourseNames();
			model.addAttribute("courseNamesList",courseNamesList);
			
			String uuidOrderId =  UUID.randomUUID().toString();
			model.addAttribute("uuidOrderId",uuidOrderId);
			
			model.addAttribute("orders",new Orders());
			return "sell-course";
		}
		
		@PostMapping("/sellCourseForm")
		public String openSellCourseForm(@ModelAttribute("orders") Orders order,@SessionAttribute("sessionEmp") Employee employee, RedirectAttributes redirectAttributes) {
			LocalDate ld = LocalDate.now();
			String pdate = ld.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			
			LocalTime lt = LocalTime.now();
			String ptime = lt.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
			
			String purchased_Date = pdate+", "+ptime;
			order.setDateOfPurchase(purchased_Date);
			
			try {
				
				EmployeeOrders employeeOrders = new EmployeeOrders(); 
				employeeOrders.setOrderId(order.getOrderId());
				employeeOrders.setEmployeeEmail(employee.getEmail());
				employeeOrdersRepository.save(employeeOrders);
				
				ordersService.storeUserOrders(order);
				redirectAttributes.addFlashAttribute("SuccessMsg", "Course has been provided successfully!!");
			}
			catch(Exception e) {
				redirectAttributes.addFlashAttribute("ErrorMsg", "Course has not been provided due to some error!!");
			}
			 return "redirect:/sellCourse";
		}
		
		//-------------inquiry management------------------------
		@GetMapping("/inquiryManagement")
		public String openIquiryManagementPage(Model model)
		{
			model.addAttribute("inquiry",new Inquiry());
			return "inquiry-management";
		}
		
		//-------------employee logout------------------------
		@GetMapping("/employeeLogout")
		public String employeeLogout(SessionStatus sessionStatus)
		{
			sessionStatus.setComplete();
			return "employee-login";
		}
				
			
//	----------------Employee sideBar ends---------------------
			
			
//	-----------------Employee management page starts-------------------
	@GetMapping("/employeeManagement")
	public String openEmployeeManagementPage(Model model,
			@RequestParam(name="page",defaultValue="0") int page,
			@RequestParam(name="size",defaultValue="5") int size
			) {
		
		
		Pageable pageable = PageRequest.of(page,size);
		Page<Employee> employeePage =  employeeService.getAllCoursesByPagination(pageable);
		model.addAttribute("employeePage", employeePage);
		return "employee-management";
	}
	
//	-----------------Employee management page starts-------------------
	
//	------------------ add Employee starts ----------------------
	
	@GetMapping("/addEmployee")
	public String openAddEmployeePage(Model model) {
		model.addAttribute("employee",new Employee());
		return "add-employee";
	}
	
	@PostMapping("/addEmployeeForm")
	public String addCourseForm(@ModelAttribute Employee employee, Model model) {
		try {
			employeeService.addEmployee(employee);
			model.addAttribute("SuccessMsg","Employee Added Successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("ErrorMsg","Employee not Added due some error");
		}
		return "add-employee";
	}
//	------------------add Employee ends ----------------------
	
	
//	------------------edit Employee starts ----------------------
	
	@GetMapping("/editEmployee")
	public String openEditEmployeePage(@RequestParam("employeeEmail") String employeeEmail,Model model) {
		
		Employee employee = employeeService.getEmployeeDetails(employeeEmail);
		model.addAttribute("employee",employee);
		model.addAttribute("newEmployeeObj",new Employee());
		return "edit-employee";
	}
	
	@PostMapping("/updateEmployeeDetailsForm")
	public String openUpdateEmployeeDetailsPage(@ModelAttribute("newEmployeeObj") Employee newEmployeeObj, RedirectAttributes redirectAttributes) throws IOException {
		
		Employee oldEmployeeObj = employeeService.getEmployeeDetails(newEmployeeObj.getEmail());
		newEmployeeObj.setId(oldEmployeeObj.getId());
		try {
			employeeService.updateEmployeeDetails(newEmployeeObj);
			redirectAttributes.addFlashAttribute("SuccessMsg","Employee details updated Successfully!!!");
		}
		catch(Exception e) {
			redirectAttributes.addFlashAttribute("ErrorMsg","Employee details not updated due to some Error!!!");
			e.printStackTrace();
		}
		
		return "redirect:/employeeManagement";
	}
//	------------------edit Employee ends ----------------------
	
	
//	------------------delete Employee starts ----------------------
	
	@GetMapping("/deleteEmployeeDetails")
	public String deleteCourseDetails(@RequestParam("employeeEmail") String employeeEmail, RedirectAttributes redirectAttributes) {
		try {
			employeeService.deleteEmployeeDetails(employeeEmail);
			redirectAttributes.addFlashAttribute("SuccessMsg","Employee deleted Successfully!!!");
		}
		catch(Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("ErrorMsg","Employee not deleted due to some Error!!!");
		}
		return "redirect:/employeeManagement";
	}
//	------------------delete Employee starts ----------------------
}
