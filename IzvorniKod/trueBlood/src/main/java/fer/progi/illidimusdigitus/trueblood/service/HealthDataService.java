package fer.progi.illidimusdigitus.trueblood.service;

import fer.progi.illidimusdigitus.trueblood.model.HealthData;

import java.util.List;
import java.util.Optional;

public interface HealthDataService {

     List<HealthData> getAllHealthData();
     Optional<HealthData> findById(long id);

}
