package in.sf.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sf.main.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{

	Employee findByEmail(String employeeEmail);
	
}
