
package com.example.vraman.infinitecube.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LoginDataClass implements Parcelable
{

    private String access_token;
    private String owner_id;
    public final static Creator<LoginDataClass> CREATOR = new Creator<LoginDataClass>() {


        @SuppressWarnings({
            "unchecked"
        })
        public LoginDataClass createFromParcel(Parcel in) {
            return new LoginDataClass(in);
        }

        public LoginDataClass[] newArray(int size) {
            return (new LoginDataClass[size]);
        }

    }
    ;

    protected LoginDataClass(Parcel in) {
        this.access_token = ((String) in.readValue((String.class.getClassLoader())));
        this.owner_id = ((String) in.readValue((String.class.getClassLoader())));
    }

    public LoginDataClass() {
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String accessToken) {
        this.access_token = accessToken;
    }

    public String getOwnerId() {
        return owner_id;
    }

    public void setOwnerId(String ownerId) {
        this.owner_id = ownerId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(access_token);
        dest.writeValue(owner_id);
    }

    public int describeContents() {
        return  0;
    }

}
