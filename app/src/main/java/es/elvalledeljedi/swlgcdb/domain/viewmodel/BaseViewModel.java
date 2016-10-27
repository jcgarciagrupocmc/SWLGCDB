package es.elvalledeljedi.swlgcdb.domain.viewmodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jcgarcia on 23/05/2016.
 */
public class BaseViewModel implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
    public BaseViewModel() {
    }

    protected BaseViewModel(Parcel in) {
    }

    public static final Parcelable.Creator<BaseViewModel> CREATOR =
            new Parcelable.Creator<BaseViewModel>() {
                public BaseViewModel createFromParcel(Parcel source) {
                    return new BaseViewModel(source);
                }

                public BaseViewModel[] newArray(int size) {
                    return new BaseViewModel[size];
                }
            };
}
