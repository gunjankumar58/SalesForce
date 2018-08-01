
package com.example.vraman.infinitecube.bean;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CreateContactDataClass implements Parcelable
{

    private String id;
    private boolean success;
    private List<Object> errors = null;
    public final static Creator<CreateContactDataClass> CREATOR = new Creator<CreateContactDataClass>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CreateContactDataClass createFromParcel(Parcel in) {
            return new CreateContactDataClass(in);
        }

        public CreateContactDataClass[] newArray(int size) {
            return (new CreateContactDataClass[size]);
        }

    }
    ;

    protected CreateContactDataClass(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.success = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.errors, (Object.class.getClassLoader()));
    }

    public CreateContactDataClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(success);
        dest.writeList(errors);
    }

    public int describeContents() {
        return  0;
    }

}
