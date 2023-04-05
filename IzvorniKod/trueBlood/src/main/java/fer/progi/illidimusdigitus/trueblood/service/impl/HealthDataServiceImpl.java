package fer.progi.illidimusdigitus.trueblood.service.impl;

import fer.progi.illidimusdigitus.trueblood.model.HealthData;
import fer.progi.illidimusdigitus.trueblood.repository.HealthDataRepository;
import fer.progi.illidimusdigitus.trueblood.service.HealthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HealthDataServiceImpl implements HealthDataService {

    @Autowired
    HealthDataRepository healthDataRepository;

    public List<HealthData> getAllHealthData() {
        return healthDataRepository.findAll();
    }

    public Optional<HealthData> findById(long id) {
        return healthDataRepository.findById(id);
    }
}
