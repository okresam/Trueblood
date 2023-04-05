package fer.progi.illidimusdigitus.trueblood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fer.progi.illidimusdigitus.trueblood.model.Blood;
import fer.progi.illidimusdigitus.trueblood.model.Consumption;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption,Long>{

}
