package fer.progi.illidimusdigitus.trueblood.controllers.util;

import fer.progi.illidimusdigitus.trueblood.model.util.BloodType;
import fer.progi.illidimusdigitus.trueblood.model.util.RoleName;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

public class EditUserInfoDTO {
    public String name;

    public String surname;

    public String address;

    public String workplace ;

    public String email;

    public String mobilePrivate;

    public String mobileBusiness;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkplace() {
        return workplace;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilePrivate() {
        return mobilePrivate;
    }

    public String getMobileBusiness() {
        return mobileBusiness;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobilePrivate(String mobilePrivate) {
        this.mobilePrivate = mobilePrivate;
    }

    public void setMobileBusiness(String mobileBusiness) {
        this.mobileBusiness = mobileBusiness;
    }
}
