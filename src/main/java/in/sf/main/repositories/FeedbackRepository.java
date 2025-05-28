package in.sf.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.sf.main.entities.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>
{

}
