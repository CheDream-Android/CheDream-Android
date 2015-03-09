package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User implements Parcelable {

    private String id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("middle_name")
    private String middleName;

    @SerializedName("last_name")
    private String lastName;

    private String birthday;

    private String about;

    @SerializedName("vkontakte_id")
    private String vkontakteId;

    @SerializedName("facebook_id")
    private String facebookId;

    @SerializedName("odnoklassniki_id")
    private String odnoklassnikiId;

    @SerializedName("financial_contributions")
    private ArrayList<FinancialContribution> financialContributions;

    @SerializedName("equipment_contributions")
    private ArrayList<FinancialContribution> equipmentContributions;

    @SerializedName("work_contributions")
    private ArrayList<FinancialContribution> workContributions;

    @SerializedName("other_contributions")
    private ArrayList<FinancialContribution> otherContributions;

    private ArrayList<Dream> dreams;

    private String phone;

    private String skype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getVkontakteId() {
        return vkontakteId;
    }

    public void setVkontakteId(String vkontakteId) {
        this.vkontakteId = vkontakteId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getOdnoklassnikiId() {
        return odnoklassnikiId;
    }

    public void setOdnoklassnikiId(String odnoklassnikiId) {
        this.odnoklassnikiId = odnoklassnikiId;
    }

    public ArrayList<FinancialContribution> getFinancialContributions() {
        return financialContributions;
    }

    public void setFinancialContributions(ArrayList<FinancialContribution> financialContributions) {
        this.financialContributions = financialContributions;
    }

    public ArrayList<FinancialContribution> getEquipmentContributions() {
        return equipmentContributions;
    }

    public void setEquipmentContributions(ArrayList<FinancialContribution> equipmentContributions) {
        this.equipmentContributions = equipmentContributions;
    }

    public ArrayList<FinancialContribution> getWorkContributions() {
        return workContributions;
    }

    public void setWorkContributions(ArrayList<FinancialContribution> workContributions) {
        this.workContributions = workContributions;
    }

    public ArrayList<FinancialContribution> getOtherContributions() {
        return otherContributions;
    }

    public void setOtherContributions(ArrayList<FinancialContribution> otherContributions) {
        this.otherContributions = otherContributions;
    }

    public ArrayList<Dream> getDreams() {
        return dreams;
    }

    public void setDreams(ArrayList<Dream> dreams) {
        this.dreams = dreams;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.middleName);
        dest.writeString(this.lastName);
        dest.writeString(this.birthday);
        dest.writeString(this.about);
        dest.writeString(this.vkontakteId);
        dest.writeString(this.facebookId);
        dest.writeString(this.odnoklassnikiId);
        dest.writeSerializable(this.financialContributions);
        dest.writeSerializable(this.equipmentContributions);
        dest.writeSerializable(this.workContributions);
        dest.writeSerializable(this.otherContributions);
        dest.writeSerializable(this.dreams);
        dest.writeString(this.phone);
        dest.writeString(this.skype);
    }

    public User() {
    }

    private User(Parcel in) {
        this.id = in.readString();
        this.firstName = in.readString();
        this.middleName = in.readString();
        this.lastName = in.readString();
        this.birthday = in.readString();
        this.about = in.readString();
        this.vkontakteId = in.readString();
        this.facebookId = in.readString();
        this.odnoklassnikiId = in.readString();
        this.financialContributions = (ArrayList<FinancialContribution>) in.readSerializable();
        this.equipmentContributions = (ArrayList<FinancialContribution>) in.readSerializable();
        this.workContributions = (ArrayList<FinancialContribution>) in.readSerializable();
        this.otherContributions = (ArrayList<FinancialContribution>) in.readSerializable();
        this.dreams = (ArrayList<Dream>) in.readSerializable();
        this.phone = in.readString();
        this.skype = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
