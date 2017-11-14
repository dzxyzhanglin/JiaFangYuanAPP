package com.songsong.jiafangyuan.data.entity;

/**
 * Created by zhanglin on 2017/9/28.
 */

public class UserEntity {
    private long userid = 0;
    private String nickName = null;
    private String avatar = null;
    private String phone = null;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
