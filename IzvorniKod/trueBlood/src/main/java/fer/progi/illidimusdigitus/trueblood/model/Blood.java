package fer.progi.illidimusdigitus.trueblood.model;

import fer.progi.illidimusdigitus.trueblood.model.util.BloodType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a Blood type. 
 * It has id, which is primary key.
 * It also has blood type name, lowerbound, 
 * upperbound and current blood supply.
 * @author david
 */

@Entity
@Table(name = "krvnaVrsta")
public class Blood {
	/**
     * Blood id
     */
    @Id
    @GeneratedValue
    @Column(name = "krvId")
    public Long id;
    
    /**
     * Blood name
     */
	@Enumerated(EnumType.STRING)
    @Column(name = "imeKrvneGrupe", nullable = false)
    public BloodType name;

	/**
	 * Blood upperbound
	 */
	@Column(name = "gornjaGranica", nullable = false)
	public int upperbound;

    /**
     * Blood lowerbound
     */
    @Column(name = "donjaGranica", nullable = false)
    public int lowerbound;


    /**
     * Current blood supply
     */
    @Column(name = "trenutnaZaliha", nullable = false)
    public int supply;

    public Blood() {
    	
    }

	public Blood(Long id, BloodType name, int lowerbound, int upperbound, int supply) {
		this.id = id;
		this.name = name;
		this.lowerbound = lowerbound;
		this.upperbound = upperbound;
		this.supply = supply;
	}
	public BloodType getName() {
		return name;
	}
	public void setName(BloodType name) {
		this.name = name;
	}
	public int getLowerbound() {
		return lowerbound;
	}
	public void setLowerbound(int lowerbound) {
		this.lowerbound = lowerbound;
	}
	public int getUpperbound() {
		return upperbound;
	}
	public void setUpperbound(int upperbound) {
		this.upperbound = upperbound;
	}
	public int getSupply() {
		return supply;
	}
	public void setSupply(int supply) {
		this.supply = supply;
	}
	public Long getId() {
		return id;
	}
	
	
	
    
    
    
    
}
