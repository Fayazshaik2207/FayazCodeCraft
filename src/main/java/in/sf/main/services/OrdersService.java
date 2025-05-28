package in.sf.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sf.main.entities.Orders;
import in.sf.main.repositories.OrdersRepository;

@Service
public class OrdersService {
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	public void storeUserOrders(Orders orders) {
		ordersRepository.save(orders);
	}
}
