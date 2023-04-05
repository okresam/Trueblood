package fer.progi.illidimusdigitus.trueblood.controllers;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fer.progi.illidimusdigitus.trueblood.controllers.util.DonationDTO;
import fer.progi.illidimusdigitus.trueblood.model.Donation;
import fer.progi.illidimusdigitus.trueblood.model.User;
import fer.progi.illidimusdigitus.trueblood.service.DonationService;
import fer.progi.illidimusdigitus.trueblood.service.UserService;

@RestController
public class DonationController {
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 private DonationService donationService;
	 
	 @CrossOrigin(origins = "*")
	 @RequestMapping(value = "/donations",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	 @Secured("ROLE_DONOR")
     public List<DonationDTO> getDonations(@RequestHeader String username) {
        User usr = userService.findByUsername(username).get();
        
        List<Donation> donations = donationService.findByDonor(usr);
        List<DonationDTO> response = new LinkedList();
        for(Donation donation: donations) {
        	DonationDTO don = new DonationDTO();
        	
        	don.setId(donation.getId());
        	don.setDonationDate(donation.getDate());
        	don.setLocation(donation.getDonationPlace());
        	don.setSuccess(donation.getSuccess());
        	
        	response.add(don);
        }
        return response;
     }
	 
	@CrossOrigin(origins = "*")
    @PostMapping("/generatePDF")
	@Secured("ROLE_DONOR")
    public ResponseEntity<String> generatePDF(@RequestHeader Long id) {
        Donation donation = donationService.findById(id).get();
        User usr = userService.findByUsername(donation.getDonor().getUsername()).get();
        
        donationService.sendPDF(donation, usr);
		return ResponseEntity.ok("PDF generated!");
    }
	
	@Scheduled(cron = "@daily")
	//@Scheduled(cron = "0 * * * * ?")
	public void donationMonths() throws Exception {
		Date threeMonthsAgo = new Date();
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(threeMonthsAgo);
		cal.add(Calendar.DATE, -90);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		List<Donation> donationsMen = donationService.findByDate(cal.getTime());
		
		donationsMen.stream()
		 .filter((e) -> e.getSuccess() == true && e.getDonor().isMale())
		 .collect(Collectors.toList());
		
		Date fourMonthsAgo = new Date();
		
		GregorianCalendar calWomen = new GregorianCalendar();
		calWomen.setTime(fourMonthsAgo);
		calWomen.add(Calendar.DATE, -120);
		calWomen.set(Calendar.HOUR_OF_DAY, 0);
		calWomen.set(Calendar.MINUTE, 0);
		calWomen.set(Calendar.SECOND, 0);
		calWomen.set(Calendar.MILLISECOND, 0);
		List<Donation> donationsWomen = donationService.findByDate(calWomen.getTime());
		
		donationsWomen.stream()
				 .filter((e) -> e.getSuccess() == true && !e.getDonor().isMale())
				 .collect(Collectors.toList());
		
		Set<User> allUsers = new HashSet<>();
		
		for(Donation don : donationsMen) {
			User usr = don.getDonor();
			allUsers.add(usr);
			System.out.println(usr.getEmail());
		}
		
		for(Donation don : donationsWomen) {
			User usr = don.getDonor();
			allUsers.add(usr);
		}
		
		
		donationService.sendPoziv(allUsers);
		
	}


}
