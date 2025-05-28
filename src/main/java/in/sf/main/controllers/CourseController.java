package in.sf.main.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sf.main.entities.Course;
import in.sf.main.services.CourseService;

@Controller
public class CourseController {

	private String uploadDir = "src/main/resources/static/uploads/" ;
	private String imageUrl = "http://localhost:8080/uploads/";
	
	@Autowired
	private CourseService courseService;
	
	
//	-----------------course management page starts-------------------
	
	@GetMapping("/courseManagement")
	public String openCourseManagementPage(Model model,
			@RequestParam(name="page",defaultValue="0") int page,
			@RequestParam(name="size",defaultValue="5") int size
			) {
		
		
		Pageable pageable = PageRequest.of(page,size);
		Page<Course> coursesPage =  courseService.getAllCoursesByPagination(pageable);
		model.addAttribute("coursesPage", coursesPage);
		return "course-management";
	}
	
//	-----------------course management page ends-------------------
	
	
//	------------------add course starts ----------------------
	
	@GetMapping("/addCourse")
	public String openAddCoursePage(Model model) {
		model.addAttribute("course",new Course());
		return "add-course";
	}
	
	@PostMapping("/addCourseForm")
	public String addCourseForm(@ModelAttribute Course course,@RequestParam("courseImg") MultipartFile courseImg, Model model) {
		try {
			courseService.addCourse(course,courseImg);
			model.addAttribute("SuccessMsg","Course Added Successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("ErrorMsg","Course not Added due some error");
		}
		return "add-course";
	}
//	------------------add course ends ----------------------
	
	
//	------------------edit course starts ----------------------
	
	@GetMapping("/editCourse")
	public String openEditCoursePage(@RequestParam("courseName") String courseName,Model model) {
		
		Course course = courseService.getCourseDetails(courseName);
		model.addAttribute("course",course);
		model.addAttribute("newCourseObj",new Course());
		return "edit-course";
	}
	
	@PostMapping("/updateCourseDetailsForm")
	public String openUpdateCourseDetailsPage(@ModelAttribute("newCourseObj") Course newCourseObj,@RequestParam("courseImg") MultipartFile courseImg, RedirectAttributes redirectAttributes) throws IOException {
		
		Course oldCourseObj = courseService.getCourseDetails(newCourseObj.getName());
		newCourseObj.setId(oldCourseObj.getId());
		try {
			if(!courseImg.isEmpty()) {
				String imgName = courseImg.getOriginalFilename();
				Path imgPath = Paths.get(uploadDir+imgName);
				Files.write(imgPath,courseImg.getBytes());
				
				String imgUrl = imageUrl+imgName;
				newCourseObj.setImageUrl(imgUrl);
			}
			else {
				newCourseObj.setImageUrl(oldCourseObj.getImageUrl());
			}
			courseService.updateCourseDetails(newCourseObj);
			redirectAttributes.addFlashAttribute("SuccessMsg","Course details updated Successfully!!!");
		}
		catch(Exception e) {
			redirectAttributes.addFlashAttribute("ErrorMsg","Course details not updated due to some Error!!!");
			e.printStackTrace();
		}
		
		return "redirect:/courseManagement";
	}
//	------------------edit course ends ----------------------
	
	
//	------------------delete course starts ----------------------
	
	@GetMapping("/deleteCourseDetails")
	public String deleteCourseDetails(@RequestParam("courseName") String courseName, RedirectAttributes redirectAttributes) {
		try {
			courseService.deleteCourseDetails(courseName);
			redirectAttributes.addFlashAttribute("SuccessMsg","Course deleted Successfully!!!");
		}
		catch(Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("ErrorMsg","Course not deleted due to some Error!!!");
		}
		return "redirect:/courseManagement";
	}
//	------------------delete course starts ----------------------
}
