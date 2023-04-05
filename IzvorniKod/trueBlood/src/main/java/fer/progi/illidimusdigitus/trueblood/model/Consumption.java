package fer.progi.illidimusdigitus.trueblood.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * This class represents blood consumption.
 * It has id, which is primary key.
 * It also has timestamp of blood consumption , quantity and hospital location.
 * @author david
 */
@Entity
@Table(name = "potrosnjaKrvi")
public class Consumption {

    /**
     * Consumption id
     */
    @Id
    @GeneratedValue
    @Column(name = "idPotrosnje")
    public Long id;
    /**
     * Consumption timestamp
     */
    @Column(name = "timestampPotrosnje", nullable = false)
    public Timestamp timestamp;

    /**
     * Blood Type
     */
    @ManyToOne()
    @JoinColumn(name = "krvId")
    public Blood bloodType;

    /**
     * Consumption quantity
     */
    @Column(name = "kolicinaJedinica", nullable = false)
    public int quantity;

    /**
     * User employee
     */
    @ManyToOne()
    @JoinColumn(name = "korisnikId")
    public User employee;

    /**
     * Consumption hospital location
     */
    @Column(name = "lokacijaPotrosnje", nullable = false)
    public String location;

    public Consumption() {

    }

    public Consumption(Timestamp timestamp, int quantity, String location, Blood bloodType, User employee) {
        super();
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.location = location;
        this.bloodType = bloodType;
        this.employee = employee;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Blood getBloodType() {
        return bloodType;
    }

    public void setBloodType(Blood bloodType) {
        this.bloodType = bloodType;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }


}
