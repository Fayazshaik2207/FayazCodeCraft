package in.sf.main.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.sf.main.services.OrdersChartService;

@Controller
public class OrdersChartController {

    @Autowired
    private OrdersChartService orderChartService;

    @GetMapping("/adminProfile")
    public String openAdminProfilePage(Model model) {

        // ------------------- Graph-1 --------------------------
        List<Object[]> coursesSoldList = orderChartService.findCoursesSoldPerDay();
        List<String> dates1 = new ArrayList<>();
        List<Long> counts1 = new ArrayList<>();

        for (Object[] obj : coursesSoldList) {
            if (obj != null) {
                dates1.add(obj[0] != null ? obj[0].toString() : "");
                counts1.add(obj[1] != null ? ((Number) obj[1]).longValue() : 0L);
            }
        }
        model.addAttribute("counts1", counts1);
        model.addAttribute("dates1", dates1);

        // ------------------- Graph-2 --------------------------
        List<Object[]> numberOfCoursesSoldList = orderChartService.findNumberOfCoursesSold();
        List<String> courseNames = new ArrayList<>();
        List<Long> courseCount = new ArrayList<>();

        for (Object[] obj : numberOfCoursesSoldList) {
            if (obj != null) {
                courseNames.add(obj[0] != null ? obj[0].toString() : "Unknown");
                courseCount.add(obj[1] != null ? ((Number) obj[1]).longValue() : 0L);
            }
        }
        model.addAttribute("courseNames", courseNames);
        model.addAttribute("courseCount", courseCount);

        return "admin-profile";
    }
}
