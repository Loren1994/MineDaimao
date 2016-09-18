package bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by loren on 16-8-9.
 */

public class User extends BaseObservable {
    private String name;
    private String sex;
    private String address;
    private boolean flag = false;
//    private ObservableField<String> obStr = new ObservableField<>();

    public User(String name, String sex, String address) {
        this.address = address;
        this.name = name;
        this.sex = sex;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Bindable
    public String getName() {
        if (!flag) {
            return name;
        } else {
            return "姓名 : " + name;
        }
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getSex() {
        if (!flag) {
            return sex;
        } else {
            return "性别 : " + sex;
        }
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Bindable
    public String getAddress() {
        if (!flag) {
            return address;
        } else {
            return "地址 : " + address;
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
