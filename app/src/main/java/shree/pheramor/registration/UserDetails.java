package shree.pheramor.registration;

import com.google.gson.annotations.Expose;

public class UserDetails {
    @Expose
    private String email, password, fullName, zipCode, height, gender, dob;
    @Expose
    private String race, religion,interestGender, interestAgeRange,imagePath;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setInterestGender(String interestGender) {
        this.interestGender = interestGender;
    }

    public void setInterestAgeRange(String interestAgeRange) {
        this.interestAgeRange = interestAgeRange;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getRace() {
        return race;
    }

    public String getReligion() {
        return religion;
    }

    public String getInterestGender() {
        return interestGender;
    }

    public String getInterestAgeRange() {
        return interestAgeRange;
    }

    public String getImagePath() {
        return imagePath;
    }
}
