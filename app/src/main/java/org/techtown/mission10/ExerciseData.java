package org.techtown.mission10;

import android.os.Parcel;
import android.os.Parcelable;

public class ExerciseData implements Parcelable{
    String routineName;


    public ExerciseData(String rn) {
        routineName = rn;
    }
    protected ExerciseData(Parcel in) {
        routineName = in.readString();
    }

    public static final Creator<ExerciseData> CREATOR = new Creator<ExerciseData>() {
        @Override
        public ExerciseData createFromParcel(Parcel in) {
            return new ExerciseData(in);
        }

        @Override
        public ExerciseData[] newArray(int size) {
            return new ExerciseData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(routineName);
    }
}
