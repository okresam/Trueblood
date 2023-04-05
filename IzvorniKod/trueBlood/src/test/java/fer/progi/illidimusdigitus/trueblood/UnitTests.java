package fer.progi.illidimusdigitus.trueblood;



import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fer.progi.illidimusdigitus.trueblood.controllers.BloodController;
import fer.progi.illidimusdigitus.trueblood.controllers.ConsumptionController;
import fer.progi.illidimusdigitus.trueblood.controllers.UserController;
import fer.progi.illidimusdigitus.trueblood.controllers.util.BloodDTO;
import fer.progi.illidimusdigitus.trueblood.controllers.util.ConsumptionDTO;
import fer.progi.illidimusdigitus.trueblood.controllers.util.UserInfoDTO;
import fer.progi.illidimusdigitus.trueblood.model.Blood;
import fer.progi.illidimusdigitus.trueblood.model.User;
import fer.progi.illidimusdigitus.trueblood.model.util.BloodType;
import fer.progi.illidimusdigitus.trueblood.model.util.RoleName;
import fer.progi.illidimusdigitus.trueblood.repository.BloodRepository;
import fer.progi.illidimusdigitus.trueblood.repository.UserRepository;
import fer.progi.illidimusdigitus.trueblood.service.BloodService;
import fer.progi.illidimusdigitus.trueblood.service.RoleService;
import fer.progi.illidimusdigitus.trueblood.service.UserService;

@SpringBootTest
public class UnitTests {
	@Autowired
	UserService userService;
	@Autowired
	UserController userController;
	@Autowired
	RoleService roleService;
	@Autowired
	BloodService bloodService;
	
	@Autowired
	BloodController bloodController;
	@Autowired
	ConsumptionController consumptionController;
	/*
	 * Updating user surname, which is allowed in our application.
	 * Before running tests, disable security in UserController.
	 */
	@Test
	public void editUserProfileCorrectly() {
		
		User user = new User("MR78912",
                "12345678",
                "Marko",
                "Radić",
                true,
                "Zagreb",
                "12345678912",
                "Radićeva 7",
                "Radnička 8",
                "markoradic@fer.hr",
                "0987412589",
                "0987412589",
                new Date(),
                
                roleService.findByName(RoleName.DONOR).get(),
                bloodService.findByName(BloodType.A_MINUS).get());
		
        userService.save(user);
        
        String newSurname="Radićević";
        UserInfoDTO userWithNewSurname = new UserInfoDTO(user.getName(),newSurname,
                user.getBirthplace(), user.getAddress(), user.getWorkplace(),
                user.getMobilePrivate(), user.getMobileBusiness(), user.getBirthdate(), false);

        String expected = newSurname;
        
        userController.getEditUserInfo(userWithNewSurname, user.getUsername());
        String result = userService.findByUsername(user.getUsername()).get().getSurname();
        Assertions.assertEquals(expected,result);
	}
	
	/*
	 * User field rejected can not be updated using userController.getEditUserInfo().
	 * Before running tests, disable security in UserController.
	 */
	@Test
	public void editUserProfileIncorrectly() {
		User user = new User("MR78912",
                "12345678",
                "Marko",
                "Radić",
                true,
                "Zagreb",
                "12345678912",
                "Radićeva 7",
                "Radnička 8",
                "markoradic@fer.hr",
                "0987412589",
                "0987412589",
                new Date(),
                roleService.findByName(RoleName.DONOR).get(),
                bloodService.findByName(BloodType.A_MINUS).get());
		
        userService.save(user);
        boolean newRejected = !user.isRejected();
        UserInfoDTO userWithNewRejected = new UserInfoDTO(user.getName(), user.getSurname(),
                user.getBirthplace(), user.getAddress(), user.getWorkplace(),
                user.getMobilePrivate(), user.getMobileBusiness(), user.getBirthdate(), newRejected);

        String expected = "400 BAD_REQUEST";
        String result = userController.getEditUserInfo(userWithNewRejected, user.getUsername()).getStatusCode().toString();
        Assertions.assertEquals(expected,result);
	}
	
	/*
	 * Try to change blood lower bound (positive integer).
	 * Before running tests, disable security in BloodController.
	 */
	@Test
	public void changeLowerboundCorrectly() {
			Blood blood = bloodService.findByName(BloodType.A_PLUS).get();
		 	int newLowerbound = 100;
		 	BloodDTO newBounds = new BloodDTO("A+", blood.getUpperbound(), newLowerbound);

	        int expected = newLowerbound;
	       
	        bloodController.changeBounds(newBounds);
	        int result = bloodService.findByName(BloodType.A_PLUS).get().getLowerbound();
	        Assertions.assertEquals(expected,result);
	}
	
	/*
	 * Try to change blood lower bound (negative integer), not allowed operation.
	 * Before running tests, disable security in BloodController.
	 */
	@Test
	public void changeLowerboundIncorrectly() {
			Blood blood = bloodService.findByName(BloodType.A_PLUS).get();
		 	
			int newLowerbound = -100;
		 	BloodDTO newBounds = new BloodDTO("A+", blood.getUpperbound(), newLowerbound);
	
		 	String expected = "400 BAD_REQUEST";
	       
	        String result = bloodController.changeBounds(newBounds).getStatusCode().toString();
	        Assertions.assertEquals(expected,result);
	        
	}
	
	/*
	 * Try to make consumption with positive quantity.
	 * Before running tests, disable security in ConsumptionController.
	 */
	@Test
	public void makeConsumptionCorrectly() {
		Blood blood = bloodService.findByName(BloodType.A_PLUS).get();
		
		User employee = new User("DR78912",
                "12345678",
                "Dario",
                "Radić",
                true,
                "Zagreb",
                "12345678912",
                "Radićeva 7",
                "Radnička 8",
                "markoradic@fer.hr",
                "0987412589",
                "0987412589",
                new Date(),
                roleService.findByName(RoleName.DJELATNIK).get(),
                bloodService.findByName(BloodType.A_MINUS).get());
		
		userService.save(employee);
		
		
	 	int quantity = 25;
	 	ConsumptionDTO newConsump = new ConsumptionDTO(blood.getName().toString(), new Timestamp(0).toString(), 
	 			quantity, "Zagreb", employee.getUsername());
	 	
	 	int expected = bloodService.findByName(blood.getName()).get().getSupply() - quantity;
	 	consumptionController.consumeBlood(newConsump);
        
        int result = bloodService.findByName(blood.getName()).get().getSupply();
        Assertions.assertEquals(expected,result);
	}
	
	/*
	 * Try to make consumption with negative quantity, not allowed operation.
	 * Before running tests, disable security in ConsumptionController.
	 */
	@Test
	public void makeConsumptionIncorrectly() {
			Blood blood = bloodService.findByName(BloodType.A_PLUS).get();
		
			User employee = new User("DR78912",
	                "12345678",
	                "Dario",
	                "Radić",
	                true,
	                "Zagreb",
	                "12345678912",
	                "Radićeva 7",
	                "Radnička 8",
	                "markoradic@fer.hr",
	                "0987412589",
	                "0987412589",
	                new Date(),
	                roleService.findByName(RoleName.DJELATNIK).get(),
	                bloodService.findByName(BloodType.A_MINUS).get());
			
			userService.save(employee);
			
		 	int quantity = -5;
		 	ConsumptionDTO newConsump = new ConsumptionDTO(blood.getName().toString(), new Timestamp(0).toString(), 
		 			quantity, "Zagreb", employee.getUsername());
		 	
		 	String expected = "400 BAD_REQUEST";
		 
	        String result = consumptionController.consumeBlood(newConsump).getStatusCode().toString();
	        Assertions.assertEquals(expected,result);
	}
	
}	
