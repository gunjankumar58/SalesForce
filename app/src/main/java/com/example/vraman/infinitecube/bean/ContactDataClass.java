
package com.example.vraman.infinitecube.bean;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

public class ContactDataClass implements Parcelable
{

    private int totalSize;
    private boolean done;
    private List<Record> records = null;

    public final static Creator<ContactDataClass> CREATOR = new Creator<ContactDataClass>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ContactDataClass createFromParcel(Parcel in) {
            return new ContactDataClass(in);
        }

        public ContactDataClass[] newArray(int size) {
            return (new ContactDataClass[size]);
        }

    }
    ;

    protected ContactDataClass(Parcel in) {
        this.totalSize = ((int) in.readValue((int.class.getClassLoader())));
        this.done = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.records, (Record.class.getClassLoader()));
    }

    public ContactDataClass() {
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalSize);
        dest.writeValue(done);
        dest.writeList(records);
    }

    public int describeContents() {
        return  0;
    }

}
