package fer.progi.illidimusdigitus.trueblood.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fer.progi.illidimusdigitus.trueblood.model.Blood;
import fer.progi.illidimusdigitus.trueblood.model.Donation;
import fer.progi.illidimusdigitus.trueblood.model.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface DonationRepository extends JpaRepository<Donation,Long>{
	
	List<Donation> findByDonor(User donor);
	
	Optional<Donation> findById(Long id);
	
	List<Donation> findByDate(Date date);

	void deleteByDonor(User user);

	//@Query("DELETE FROM Donation WHERE id = ?1; DELETE FROM HealthDataAnswered WHERE donation = ?1; ")
	void delete(Donation donation);

	@Query("SELECT MAX(date) FROM Donation WHERE donor.username = ?1 AND success = true")
	Date getLastSuccessfulDonationDate(String username);
}
