package fer.progi.illidimusdigitus.trueblood.model;


import javax.persistence.*;
import java.util.Date;

/**
 * This class represents User.
 * User username which is primary key.
 * user also has name, surname, birthplace, birthdate
 * address, workplace, email, mobilePrivate, 
 * mobileBusiness, bloodId which represents blood type and roleId 
 * which is his role in the system.
 * @author david
 */

@Entity
@Table(name = "korisnikAplikacije")
public class User {

    /**
     * User username
     */
	@Id
    @Column(name = "korisnikId", unique = true, nullable = false)
    public String username;
    
    /**
     * User password
     */
    @Column(name = "lozinka", nullable = false)
    public String password;

    /**
     * User name
     */
    @Column(name = "ime", nullable = false)
    public String name;

    /**
     * User surname
     */
    @Column(name = "prezime", nullable = false)
    public String surname;
    
    /**
     * User birthplace
     */
    @Column(name = "mjestoRodenja")
    public String birthplace;
    
    /**
     * User oib
     */
    @Column(name = "oib", nullable = false)
    public String oib;
    
    /**
     * User address
     */
    @Column(name = "adresaStanovanja")
    public String address;
    
    /**
     * User workplace
     */
    @Column(name = "mjestoZaposlenja")
    public String workplace ;
    
    /**
     * User email
     */
    @Column(name = "email")
    public String email;

    /**
     * User mobilePrivate
     */
    @Column(name = "brojMobitelaPrivatni")
    public String mobilePrivate;
    
    /**
     * User mobileBusiness
     */
    @Column(name = "brojMobitelaPoslovni")
    public String mobileBusiness;
    
    /**
     * User birthdate
     */
    @Column(name = "datumRodenja")
    public Date birthdate;
    
    /**
     * User rejected
     */
    @Column(name = "trajnoOdbijenoDarivanje")
    public boolean rejected;
    
    /**
     * User activation
     */
    @Column(name = "aktivacijskiKljuc")
    public String activation;

	/**
	 * User gender
	 */
	@Column(name="spol")
	public boolean gender;

    /**
     * Blood Type
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "krvId")
    public Blood bloodType;
    

    /**
     * Role 
     */
    @ManyToOne()
    @JoinColumn(name = "ulogaId")
    public Role role;
    
    public User() {
    }
    
    
    public User(String username, String password, String name, String surname, boolean male,
                    String birthplace, String oib, String address, String workplace,
                    String email, String mobilePrivate, String mobileBusiness, Date birthdate,
                    Role role, Blood bloodType) {

		this.gender = male;
        this.username = username;
		this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthplace = birthplace;
        this.email = email;
        this.oib = oib;
        this.address = address;
		this.workplace = workplace;
        this.mobilePrivate = mobilePrivate;
        this.mobileBusiness = mobileBusiness;
        this.birthdate = birthdate;
        this.role = role;
		this.bloodType = bloodType;
		this.rejected = false;
		this.activation = oib.substring(1);
    }
    
    
    public User(String username,String password, String name, String surname,
            String oib, Role role, Blood bloodType, String email,boolean male,
			String workplace, String mobilePrivate, String mobileBusiness, Date birthdate,
			String address, String birthplace) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.oib = oib;
		this.role = role;
		this.bloodType = bloodType;
		this.email = email;
		this.gender = male;
		this.workplace = workplace;
        this.mobilePrivate = mobilePrivate;
        this.mobileBusiness = mobileBusiness;
        this.birthdate = birthdate;
		this.address = address;
		this.birthplace = birthplace;
        this.activation = oib.substring(1);
    }
    
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getBirthplace() {
		return birthplace;
	}


	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getWorkplace() {
		return workplace;
	}


	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobilePrivate() {
		return mobilePrivate;
	}


	public void setMobilePrivate(String mobilePrivate) {
		this.mobilePrivate = mobilePrivate;
	}


	public String getMobileBusiness() {
		return mobileBusiness;
	}


	public void setMobileBusiness(String mobileBusiness) { this.mobileBusiness = mobileBusiness; }


	public Date getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}


	public boolean isRejected() {
		return rejected;
	}


	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}


	public String getActivation() {
		return activation;
	}


	public void setActivation(String activation) {
		this.activation = activation;
	}


	public Blood getBloodType() {
		return bloodType;
	}


	public void setBloodType(Blood bloodType) {
		this.bloodType = bloodType;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public String getOib() {
		return oib;
	}

	public boolean isMale() {return gender;}

	public void setGender(boolean male){
		this.gender = male;
	}
}
