package in.sf.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sf.main.repositories.EmployeeSalesInfoRepository;

@Service
public class EmployeeSalesInfoService {
	
	@Autowired
	private EmployeeSalesInfoRepository employeeSalesInfoRepository;
	
	public String findTotalSalesByEmployeeOrders() {
		String totalSales = employeeSalesInfoRepository.findTotalSalesByAllEmployees();
		return totalSales;
	}
	
	public List<Object[]> findTotalSalesByEachEmployee() {
		return employeeSalesInfoRepository.findTotalSalesByEachEmployees();
	}
}
