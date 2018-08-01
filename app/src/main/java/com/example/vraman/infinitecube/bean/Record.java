
package com.example.vraman.infinitecube.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Record implements Parcelable
{

    private Attributes attributes;
    private String Name;
    private String Id;
    private String OwnerId;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }


    private String Email;
    private String Phone;
    public final static Creator<Record> CREATOR = new Creator<Record>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        public Record[] newArray(int size) {
            return (new Record[size]);
        }

    }
    ;

    protected Record(Parcel in) {
        this.attributes = ((Attributes) in.readValue((Attributes.class.getClassLoader())));
        this.Name = ((String) in.readValue((String.class.getClassLoader())));
        this.Id = ((String) in.readValue((String.class.getClassLoader())));
        this.OwnerId = ((String) in.readValue((String.class.getClassLoader())));
        this.Email = ((String) in.readValue((String.class.getClassLoader())));
        this.Phone = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Record() {
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        this.OwnerId = ownerId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(attributes);
        dest.writeValue(Name);
        dest.writeValue(Id);
        dest.writeValue(OwnerId);
        dest.writeValue(Email);
        dest.writeValue(Phone);
    }

    public int describeContents() {
        return  0;
    }

}
