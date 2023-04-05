package fer.progi.illidimusdigitus.trueblood.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fer.progi.illidimusdigitus.trueblood.controllers.util.ConsumptionDTO;
import fer.progi.illidimusdigitus.trueblood.model.Blood;
import fer.progi.illidimusdigitus.trueblood.model.Consumption;
import fer.progi.illidimusdigitus.trueblood.model.util.BloodType;
import fer.progi.illidimusdigitus.trueblood.repository.BloodRepository;
import fer.progi.illidimusdigitus.trueblood.repository.ConsumptionRepository;
import fer.progi.illidimusdigitus.trueblood.service.ConsumptionService;


@Service
public class ConsumptionServiceImpl implements ConsumptionService{
	@Autowired
    private ConsumptionRepository consumRepo;

	@Override
	public void makeConsump(Consumption consumption) {
		consumRepo.save(consumption);
	}

	
	
	
}
