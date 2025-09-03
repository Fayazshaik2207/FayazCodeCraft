package in.sf.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.sf.main.entities.EmployeeOrders;
@Repository
public interface EmployeeSalesInfoRepository extends JpaRepository<EmployeeOrders,Long>{
	
	String sql_query1 = "select sum(course_amount::numeric) as total_sales_amount from orders where order_id not like 'order_%';";
	@Query(value = sql_query1,nativeQuery = true)
	String findTotalSalesByAllEmployees();
	
	String sql_query2 = "select e.name as employee_name, e.email as employee_email, e.phoneno as employee_phoneno, " +
                   "sum(o.course_amount::numeric) as total_sales " +
                   "from employee e " +
                   "join employee_orders eo on e.email = eo.employee_email " +
                   "join orders o on eo.order_id = o.order_id " +
                   "group by e.name, e.email, e.phoneno;";
@Query(value = sql_query2,nativeQuery = true)
	List<Object[]> findTotalSalesByEachEmployees();
	
}
