package fer.progi.illidimusdigitus.trueblood.controllers.util;

import fer.progi.illidimusdigitus.trueblood.model.util.BloodType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

public class CreateUserDTO {

    public String name;

    public String surname;

    public String birthplace;

    public String oib;

    public String address;

    public String workplace ;

    public String email;

    public String mobilePrivate;

    public String mobileBusiness;

    public Date birthdate;

    public String bloodTypeName;

    public boolean rejected;

    public boolean genderMale;

    public boolean isGenderMale() {
        return genderMale;
    }

    public void setGenderMale(boolean genderMale) {
        this.genderMale = genderMale;
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

    public String getOib() {
        return oib;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public String getBloodTypeName() {
        return bloodTypeName;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setOib(String oib) {
        this.oib = oib;
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

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setBloodTypeName(String bloodTypeName) {
        this.bloodTypeName = bloodTypeName;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public boolean getRejected() {
        return rejected;
    }
}
