package fer.progi.illidimusdigitus.trueblood.service.impl;

import fer.progi.illidimusdigitus.trueblood.model.Donation;
import fer.progi.illidimusdigitus.trueblood.model.HealthDataAnswered;
import fer.progi.illidimusdigitus.trueblood.model.HealthDataAnsweredId;
import fer.progi.illidimusdigitus.trueblood.repository.HealthDataAnsweredRepository;
import fer.progi.illidimusdigitus.trueblood.service.HealthDataAnsweredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HealthDataAnsweredServiceImpl implements HealthDataAnsweredService {

    @Autowired
    HealthDataAnsweredRepository healthDataAnsweredRepository;

    @Override
    public void save(long br_donacije, long id_zdravstvenih, boolean odgovor) {
        healthDataAnsweredRepository.saveAnswertoHealthData( br_donacije,  id_zdravstvenih,  odgovor);
    }

    public Optional<HealthDataAnswered> findById(HealthDataAnsweredId healthDataAnsweredId) {
        return healthDataAnsweredRepository.findById(healthDataAnsweredId);
    }

    @Override
    public void deleteAllByDonation(Donation donation) {
        healthDataAnsweredRepository.deleteAllByDonation(donation);
    }
}
