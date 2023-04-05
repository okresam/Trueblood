package fer.progi.illidimusdigitus.trueblood.service;

import fer.progi.illidimusdigitus.trueblood.model.Donation;
import fer.progi.illidimusdigitus.trueblood.model.HealthDataAnswered;
import fer.progi.illidimusdigitus.trueblood.model.HealthDataAnsweredId;

import java.util.Optional;

public interface HealthDataAnsweredService {

    void save(long br_donacije, long id_zdravstvenih, boolean odgovor);

    Optional<HealthDataAnswered> findById(HealthDataAnsweredId healthDataAnsweredId);

    void deleteAllByDonation(Donation donation);
}