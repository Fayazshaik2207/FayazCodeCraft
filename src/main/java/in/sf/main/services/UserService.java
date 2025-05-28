package in.sf.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sf.main.entities.User;
import in.sf.main.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public void userRegistration(User user) {
		userRepository.save(user);
	}
	public boolean userLogin(String email,String password) {
		
		User user = userRepository.findByEmail(email);
		if(user != null) {
			return password.equals(user.getPassword());
		}
		return false;
		
	}
}
