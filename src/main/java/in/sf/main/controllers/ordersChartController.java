package in.sf.main.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.sf.main.services.OrdersChartService;

@Controller
public class ordersChartController {
	
	@Autowired
	private OrdersChartService orderChartService;
	
	@GetMapping("/adminProfile")
	public String openAdminProfilePage(Model model) {
		
//		-------------------graph-1 --------------------------
		List<Object[]> coursesSoldList = orderChartService.findCoursesSoldPerDay();
		List<String> dates1 = new ArrayList<>();
		List<Long> counts1 = new ArrayList<>();
		for(Object[] obj : coursesSoldList) {
			dates1.add((String)obj[0]);
			counts1.add((Long)obj[1]);
		}
		model.addAttribute("counts1",counts1);
		model.addAttribute("dates1",dates1);
		
//		-------------------graph-2 --------------------------
		
		List<Object[]> numberOfCoursesSoldList = orderChartService.findNumberOfCoursesSold();
		List<String> courseNames = new ArrayList<>();
		List<Long> courseCount = new ArrayList<>();
		for(Object[] obj : numberOfCoursesSoldList) {
			courseNames.add((String)obj[0]);
			courseCount.add((Long)obj[1]);
		}
		model.addAttribute("courseNames",courseNames);
		model.addAttribute("courseCount",courseCount);
		
  		return "admin-profile";
	}
}
