package in.sf.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import in.sf.main.entities.User;

public interface CustomerRepository extends JpaRepository<User, Long> 
{
	User findByEmail(String email);
}
