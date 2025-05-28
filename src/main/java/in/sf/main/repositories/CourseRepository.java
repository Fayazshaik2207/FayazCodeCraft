package in.sf.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sf.main.entities.Course;

public interface CourseRepository extends JpaRepository<Course,Long>{
	public Course findByName(String courseName);
}
