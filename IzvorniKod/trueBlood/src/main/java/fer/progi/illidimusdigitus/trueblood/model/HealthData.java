package fer.progi.illidimusdigitus.trueblood.model;


import javax.persistence.*;

/**
 * This class represents health data.
 * It has id, which is primary key.
 * It also has a name and a criterion.
 * @author matija
 */
@Entity
@Table(name = "zdravstveniPodaci")
public class HealthData {

    /**
     * healthData id
     */
    @Id
    @GeneratedValue
    @Column(name = "idZdravstevnih")
    public Long idHealth;

    /**
     * healthData name
     */
    @Column(name = "zdravstevniPodatak", nullable = false)
    public String name;

    /**
     * healthData criterion
     */
    @Column(name = "tezinaKriterija", nullable = false)
    public boolean criterion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getIdHealth() {
        return idHealth;
    }

    public void setIdHealth(Long idHealth) {
        this.idHealth = idHealth;
    }

    public HealthData(Long idHealth,String name,boolean criterion) {
        this.name = name;
        this.criterion = criterion;
        this.idHealth = idHealth;
    }

    public boolean getCriterion() {
        return criterion;
    }

    public void setCriterion(boolean criterion) {
        this.criterion = criterion;
    }

    public HealthData() {
    }
}