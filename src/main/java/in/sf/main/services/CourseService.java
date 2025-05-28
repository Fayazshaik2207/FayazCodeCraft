package in.sf.main.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.sf.main.entities.Course;
import in.sf.main.repositories.CourseRepository;

@Service
public class CourseService {

	private String uploadDir = "src/main/resources/static/uploads/" ;
	private String imageUrl = "http://localhost:8080/uploads/";
	
	@Autowired
	private CourseRepository courseRepository;
	
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}
//	pageable is used to specify pagination information like page number
//	Page is a chunk of data fetched according to pagination number defined by pageable
	public Page<Course> getAllCoursesByPagination(Pageable pageable) {
		return courseRepository.findAll(pageable);
	}
	public Course getCourseDetails(String courseName) {
		return courseRepository.findByName(courseName);
	}
	
	public void addCourse(Course course,MultipartFile courseImg) throws IOException {
		
		String imgName = courseImg.getOriginalFilename();
		Path imgPath = Paths.get(uploadDir+imgName);
		Files.write(imgPath,courseImg.getBytes());
		
		String imgUrl = imageUrl+imgName;
		course.setImageUrl(imgUrl);
		
		courseRepository.save(course);
	}
	
	public void updateCourseDetails(Course course) {
		courseRepository.save(course);
	}
	
	public void deleteCourseDetails(String courseName) {
		Course course =  courseRepository.findByName(courseName);
		
		if(course != null) {
			courseRepository.delete(course);
		}
		else {
			throw new RuntimeException("Course not found");
		}
	}
	
	public List<String> getAllCourseNames() {
		List<Course> coursesList = courseRepository.findAll();
		List<String> courseNamesList = new ArrayList<>();
		for(Course course : coursesList) {
			courseNamesList.add(course.getName());
		}
		return courseNamesList;
	}
}
