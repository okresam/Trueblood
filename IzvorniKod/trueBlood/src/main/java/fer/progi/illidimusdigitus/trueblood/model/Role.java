package fer.progi.illidimusdigitus.trueblood.model;

import fer.progi.illidimusdigitus.trueblood.model.util.RoleName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents role in the system.
 * It has id, which is primary key.
 * It also has name.
 * @author david
 */

@Entity
@Table(name = "uloge")
public class Role {

	/**
     * Role id
     */
    @Id
    @GeneratedValue
    @Column(name = "ulogaId")
    public Long id;
    
    /**
     * role name
     */
	@Enumerated(EnumType.STRING)
    @Column(name = "ulogaName", nullable = false)
    public RoleName name;


	public Role() {

	}

	public Role(RoleName name) {
		this.name = name;
	}

	public RoleName getName() {
		return name;
	}

	public void setName(RoleName name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
	
    
}
