package fer.progi.illidimusdigitus.trueblood.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Objects;

public class HealthDataAnsweredId implements Serializable {

    @OnDelete(action = OnDeleteAction.CASCADE)
    private Donation donation;

    @OnDelete(action = OnDeleteAction.CASCADE)
    private HealthData healthData;

    public HealthDataAnsweredId(Donation donation, HealthData healthData) {
        this.donation = donation;
        this.healthData = healthData;
    }

    public HealthDataAnsweredId() {
    }

    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

    public HealthData getHealthData() {
        return healthData;
    }

    public void setHealthData(HealthData healthData) {
        this.healthData = healthData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthDataAnsweredId that = (HealthDataAnsweredId) o;
        return Objects.equals(donation, that.donation) && Objects.equals(healthData, that.healthData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donation, healthData);
    }
}
