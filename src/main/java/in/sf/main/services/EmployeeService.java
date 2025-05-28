package in.sf.main.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import in.sf.main.entities.Employee;
import in.sf.main.repositories.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<Employee> getAllCourses() {
		return employeeRepository.findAll();
	}
	
	public Page<Employee> getAllCoursesByPagination(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}
	
	public Employee getEmployeeDetails(String employeeEmail) {
		return employeeRepository.findByEmail(employeeEmail);
	}
	
	public void addEmployee(Employee employee){
		employeeRepository.save(employee);
	}
	
	public void updateEmployeeDetails(Employee employee) {
		employeeRepository.save(employee);
	}
	
	public void deleteEmployeeDetails(String employeeEmail) {
		Employee employee =  employeeRepository.findByEmail(employeeEmail);
		
		if(employee != null) {
			employeeRepository.delete(employee);
		}
		else {
			throw new RuntimeException("Employee not found");
		}
	}

	public boolean loginEmpService(String email, String password)
	{
		Employee employee = employeeRepository.findByEmail(email);
		if(employee != null)
		{
			return password.equals(employee.getPassword());
		}
		return false;
	}
}
