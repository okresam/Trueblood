package fer.progi.illidimusdigitus.trueblood.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
/**
 * This class represents answers for specific health data.
 * It has donation id and health data id, which are primary keys.
 * It also has an answer.
 * @author matija
 */
@Entity
@Table(name = "doniranjeZdravljeOdgovori")
@IdClass(HealthDataAnsweredId.class)
public class HealthDataAnswered implements Serializable{


    /**
     * Donation id
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brDoniranja")
    @Id
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Donation donation;


    /**
     * HealthData id
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idZdravstvenih")
    @Id
    @OnDelete(action = OnDeleteAction.CASCADE)
    public HealthData healthData;


    /**
     * HealthData answer
     */
    @Column(name = "odgovorDonora", nullable = false)
    public boolean donorAnswer;


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

    public boolean isDonorAnswer() {
        return donorAnswer;
    }

    public void setDonorAnswer(boolean donorAnswer) {
        this.donorAnswer = donorAnswer;
    }

    public HealthDataAnswered(Donation donation, HealthData healthData, boolean donorAnswer) {
        this.donation = donation;
        this.healthData = healthData;
        this.donorAnswer = donorAnswer;
    }

    public HealthDataAnswered() {
    }
}
