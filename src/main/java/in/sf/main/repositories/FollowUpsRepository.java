package in.sf.main.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sf.main.entities.FollowUps;
import jakarta.persistence.Entity;

@Repository
public interface FollowUpsRepository extends JpaRepository<FollowUps,Long>{
	public Optional<FollowUps> findByPhoneno(String phoneno);
	public List<FollowUps> findByEmpEmailAndFollowUpDate(String empEmail,String followUpDate);
}
