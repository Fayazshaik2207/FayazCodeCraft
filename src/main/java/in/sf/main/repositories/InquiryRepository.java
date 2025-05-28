package in.sf.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sf.main.entities.Inquiry;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry,Long>{
	
	List<Inquiry> findByPhoneno(String phoneno);
}
