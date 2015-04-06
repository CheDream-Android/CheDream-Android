package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.chedream.android.database.RealmUser;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {

    private int id;

    private String username;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    private String birthday;

    private String about;

    private Picture avatar;

    @SerializedName("vkontakte_id")
    private String vkontakteId;

    @SerializedName("facebook_id")
    private String facebookId;

    @SerializedName("financial_contributions")
    private ArrayList<FinancialContribution> financialContributions;

    @SerializedName("equipment_contributions")
    private ArrayList<EquipmentContribution> equipmentContributions;

    @SerializedName("work_contributions")
    private ArrayList<WorkContribution> workContributions;

    @SerializedName("other_contributions")
    private ArrayList<OtherContribution> otherContributions;

    private ArrayList<Dream> dreams;

    private String phone;

    private String skype;
    /**
     * Current constructor will cast RealmUser to User
     */
    public User (RealmUser realmUser) {
        this.id = realmUser.getId();
        this.username = realmUser.getUsername();
        this.firstName = realmUser.getFirstName();
        this.lastName = realmUser.getLastName();
        getAvatar().setProviderReference(realmUser.getAvatar());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public Picture getAvatar() {
        return avatar;
    }

    public void setAvatar(Picture avatar) {
        this.avatar = avatar;
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

    public ArrayList<FinancialContribution> getFinancialContributions() {
        return financialContributions;
    }

    public void setFinancialContributions(ArrayList<FinancialContribution> financialContributions) {
        this.financialContributions = financialContributions;
    }

    public ArrayList<EquipmentContribution> getEquipmentContributions() {
        return equipmentContributions;
    }

    public void setEquipmentContributions(ArrayList<EquipmentContribution> equipmentContributions) {
        this.equipmentContributions = equipmentContributions;
    }

    public ArrayList<WorkContribution> getWorkContributions() {
        return workContributions;
    }

    public void setWorkContributions(ArrayList<WorkContribution> workContributions) {
        this.workContributions = workContributions;
    }

    public ArrayList<OtherContribution> getOtherContributions() {
        return otherContributions;
    }

    public void setOtherContributions(ArrayList<OtherContribution> otherContributions) {
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
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.birthday);
        dest.writeString(this.about);
        dest.writeParcelable(this.avatar, flags);
        dest.writeString(this.vkontakteId);
        dest.writeString(this.facebookId);
        dest.writeString(this.phone);
        dest.writeString(this.skype);

        dest.writeTypedList(this.financialContributions);
        dest.writeTypedList(this.equipmentContributions);
        dest.writeTypedList(this.workContributions);
        dest.writeTypedList(this.otherContributions);
        dest.writeTypedList(this.dreams);
    }

    public User() {
    }

    private User(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.birthday = in.readString();
        this.about = in.readString();
        this.avatar = in.readParcelable(Picture.class.getClassLoader());
        this.vkontakteId = in.readString();
        this.facebookId = in.readString();
        this.phone = in.readString();
        this.skype = in.readString();

        financialContributions = in.createTypedArrayList(FinancialContribution.CREATOR);
        equipmentContributions = in.createTypedArrayList(EquipmentContribution.CREATOR);
        workContributions = in.createTypedArrayList(WorkContribution.CREATOR);
        otherContributions = in.createTypedArrayList(OtherContribution.CREATOR);
        dreams = in.createTypedArrayList(Dream.CREATOR);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
