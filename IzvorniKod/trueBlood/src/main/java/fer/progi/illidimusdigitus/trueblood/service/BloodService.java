package fer.progi.illidimusdigitus.trueblood.service;

import fer.progi.illidimusdigitus.trueblood.controllers.util.BloodDTO;
import fer.progi.illidimusdigitus.trueblood.controllers.util.ConsumptionDTO;
import fer.progi.illidimusdigitus.trueblood.controllers.util.EditUserInfoDTO;
import fer.progi.illidimusdigitus.trueblood.model.Blood;
import fer.progi.illidimusdigitus.trueblood.model.util.BloodType;

import java.util.List;
import java.util.Optional;

public interface BloodService {

    Optional<Blood> findByName(BloodType name);
    List<Blood> findAll();
    
    boolean updateBounds(BloodType type,BloodDTO bloodDTO);
    boolean consume(BloodType type, ConsumptionDTO dto);
	void sendNotifLower(Blood blood);
	void sendNotifUpper(Blood blood);
	void incrementSupply(Blood blood, int size);
	
}
