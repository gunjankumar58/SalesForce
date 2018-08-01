
package com.example.vraman.infinitecube.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Attributes implements Parcelable
{

    private String type;
    private String url;
    public final static Creator<Attributes> CREATOR = new Creator<Attributes>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Attributes createFromParcel(Parcel in) {
            return new Attributes(in);
        }

        public Attributes[] newArray(int size) {
            return (new Attributes[size]);
        }

    }
    ;

    protected Attributes(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Attributes() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(url);
    }

    public int describeContents() {
        return  0;
    }

}
