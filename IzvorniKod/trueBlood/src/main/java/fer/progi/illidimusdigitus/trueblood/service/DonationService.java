package fer.progi.illidimusdigitus.trueblood.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fer.progi.illidimusdigitus.trueblood.model.Donation;
import fer.progi.illidimusdigitus.trueblood.model.User;

public interface DonationService {

	List<Donation> findByDonor(User donor);

	Optional<Donation> findById(Long id);

	void sendPDF(Donation donation, User usr);

	List<Donation> findByDate(Date date);

	void sendPoziv(Set<User> allUsers);

	void save(Donation donation);

	void deleteWhereUsername(User user);

	void  delete(Donation donation);

	Date getLastSuccessfulDonationDate(String username);
}
