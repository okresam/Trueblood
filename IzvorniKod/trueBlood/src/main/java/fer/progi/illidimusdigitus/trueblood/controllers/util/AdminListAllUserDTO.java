package fer.progi.illidimusdigitus.trueblood.controllers.util;

import java.util.Date;
import fer.progi.illidimusdigitus.trueblood.model.*;

public class AdminListAllUserDTO {

    public String name;

    public String surname;

    public Role role;

    public Blood blood;

    public String username;

    public boolean rejected;

    public String birthplace;

    public String address;

    public String workplace;

    public String mobilePrivate;

    public String mobileBusiness;

    public Date birthdate;

    public String getUsername() {return  username;}

    public boolean getRejected() {return rejected;}

    public Blood getBlood() {return blood;}

    public Role getRole() {return role;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public void setBlood(Blood blood) {
        this.blood = blood;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkplace() {
        return workplace;
    }

    public String getMobilePrivate() {
        return mobilePrivate;
    }

    public String getMobileBusiness() {
        return mobileBusiness;
    }

    public Date getBirthdate() { return birthdate; }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public void setMobilePrivate(String mobilePrivate) {
        this.mobilePrivate = mobilePrivate;
    }

    public void setMobileBusiness(String mobileBusiness) {
        this.mobileBusiness = mobileBusiness;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }


}
