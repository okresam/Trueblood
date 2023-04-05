package fer.progi.illidimusdigitus.trueblood.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import fer.progi.illidimusdigitus.trueblood.model.Donation;
import fer.progi.illidimusdigitus.trueblood.model.User;
import fer.progi.illidimusdigitus.trueblood.repository.DonationRepository;
import fer.progi.illidimusdigitus.trueblood.service.DonationService;
import fer.progi.illidimusdigitus.trueblood.service.EmailService;

@Service
public class DonationServiceImpl implements DonationService {
	
	@Autowired
	private DonationRepository donationRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public List<Donation> findByDonor(User donor) {
		
		return donationRepo.findByDonor(donor);
	}

	@Override
	public Optional<Donation> findById(Long id) {
		return donationRepo.findById(id);
	}
	
	@Override
    @Async
	public void sendPDF(Donation donation, User usr) {
		emailService.sendgeneratedPDF(donation, usr);
	}

	@Override
	public List<Donation> findByDate(Date date) {
		return donationRepo.findByDate(date);
	}

	@Override
	public void sendPoziv(Set<User> allUsers) {
		emailService.sendPoziv(allUsers);
		
	}

	@Override
	public void save(Donation donation) {
		donationRepo.save(donation);
	}

	@Override
	public void deleteWhereUsername(User user) {
		donationRepo.deleteByDonor(user);

	}

	@Override
	public void delete(Donation donation) {
		donationRepo.delete(donation);
	}

	@Override
	public Date getLastSuccessfulDonationDate(String username) { return donationRepo.getLastSuccessfulDonationDate(username); }
}
