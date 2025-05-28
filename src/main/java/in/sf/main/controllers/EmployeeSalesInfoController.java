package in.sf.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.sf.main.services.EmployeeSalesInfoService;

@Controller
public class EmployeeSalesInfoController {
	
	@Autowired
	private EmployeeSalesInfoService employeeSalesInfoService;
	
	@GetMapping("/sales")
	public String openSalesPage(Model model) {
		
		String totalSales = employeeSalesInfoService.findTotalSalesByEmployeeOrders();
		model.addAttribute("totalSales",totalSales);
		
		List<Object[]> salesList = employeeSalesInfoService.findTotalSalesByEachEmployee();
		model.addAttribute("salesList",salesList);
		return "sales";
	}
}
