package in.sf.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.sf.main.entities.Orders;

@Repository
public interface OrdersChartRepository extends JpaRepository<Orders,Long> {
	String sql_query1 = "select substring_index(date_of_purchase,',',1) as purchased_date, count(*) as number_of_courses from orders group by purchased_date order by purchased_date;";
	@Query(value = sql_query1, nativeQuery = true)
	List<Object[]> findCoursesSoldPerDay();
	
	
	String sql_query2 = "select course_name,count(*) as total_sales from orders group by course_name";
	@Query(value = sql_query2, nativeQuery = true)
	List<Object[]> findNumberOfCoursesSold();
}
