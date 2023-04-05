package fer.progi.illidimusdigitus.trueblood.service.impl;

import fer.progi.illidimusdigitus.trueblood.controllers.util.BloodDTO;
import fer.progi.illidimusdigitus.trueblood.controllers.util.ConsumptionDTO;
import fer.progi.illidimusdigitus.trueblood.model.Blood;
import fer.progi.illidimusdigitus.trueblood.model.Role;
import fer.progi.illidimusdigitus.trueblood.model.User;
import fer.progi.illidimusdigitus.trueblood.model.util.BloodType;
import fer.progi.illidimusdigitus.trueblood.model.util.RoleName;
import fer.progi.illidimusdigitus.trueblood.repository.BloodRepository;
import fer.progi.illidimusdigitus.trueblood.service.BloodService;
import fer.progi.illidimusdigitus.trueblood.service.EmailService;
import fer.progi.illidimusdigitus.trueblood.service.RoleService;
import fer.progi.illidimusdigitus.trueblood.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class BloodServiceImpl implements BloodService{
	@Autowired
    private BloodRepository bloodRepo;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;
    
    @Override
    public Optional<Blood> findByName(BloodType name) {
        return bloodRepo.findByNameOrderById(name);
    }

    @Override
    public List<Blood> findAll() {
       return bloodRepo.findAll();
    }
    
    public boolean updateBounds(BloodType type,BloodDTO bloodDTO){

        Optional<Blood> blood = findByName(type);

        if(blood.isEmpty())
            return false;

        Blood currBlood = blood.get();
        currBlood.setLowerbound(bloodDTO.getLowerbound());
        currBlood.setUpperbound(bloodDTO.getUpperbound());
        bloodRepo.save(currBlood);

        return true;
    }

	@Override
	public boolean consume(BloodType type, ConsumptionDTO dto) {
		Optional<Blood> blood = findByName(type);

        if(blood.isEmpty())
            return false;

        Blood currBlood = blood.get();
        int value = currBlood.getSupply() - dto.getQuantity();
        
        if(value < 0) return false;
        currBlood.setSupply(value);
        bloodRepo.save(currBlood);

        return true;
	}

	@Override
	public void sendNotifLower(Blood blood) {
		List<User> users = new LinkedList<User>();
		
		Role role = roleService.findByName(RoleName.DJELATNIK).get();
		
		users.addAll(userService.findByBloodType(blood));
		users.addAll(userService.findByRole(role));
		
		emailService.notificationLower(blood, users);
	}

	@Override
	public void sendNotifUpper(Blood blood) {
		List<User> users = new LinkedList<User>();
		
		Role role = roleService.findByName(RoleName.DJELATNIK).get();
		
		users.addAll(userService.findByRole(role));
		
		emailService.notificationUpper(blood, users);
	}

	

	@Override
	public void incrementSupply(Blood blood, int size) {
		Optional<Blood> bloodSupply = findByName(blood.getName());
		
        Blood currBlood = bloodSupply.get();
        System.out.println(currBlood.getName());
        currBlood.setSupply(currBlood.getSupply() + size);
        bloodRepo.save(currBlood);
		
	}
}
