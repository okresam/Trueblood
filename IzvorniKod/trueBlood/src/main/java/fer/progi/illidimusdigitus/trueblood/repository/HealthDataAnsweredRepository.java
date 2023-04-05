package fer.progi.illidimusdigitus.trueblood.repository;

import fer.progi.illidimusdigitus.trueblood.model.Donation;
import fer.progi.illidimusdigitus.trueblood.model.HealthDataAnswered;
import fer.progi.illidimusdigitus.trueblood.model.HealthDataAnsweredId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface HealthDataAnsweredRepository extends JpaRepository<HealthDataAnswered,HealthDataAnsweredId> {

    @Modifying
    @Query(
          value="INSERT INTO doniranje_zdravlje_odgovori (odgovor_donora,br_doniranja,id_zdravstvenih) VALUES (:odgovor_donora,:br_doniranja,:id_zdravstvenih)",
          nativeQuery = true
    )
    void saveAnswertoHealthData(@Param("br_doniranja")long br_donacije,@Param("id_zdravstvenih") long id_zdravstvenih,@Param("odgovor_donora") boolean odgovor);


    void deleteAllByDonation(Donation donation);
}
