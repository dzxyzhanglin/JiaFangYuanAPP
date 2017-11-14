package com.songsong.jiafangyuan.data.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.songsong.jiafangyuan.base.BaseBackFragment;

/**
 * Created by zhanglin on 2017/8/23.
 */

public class ListUserBean implements MultiItemEntity {
    private int mItemType = 0;
    private int mId = 0;
    private BaseBackFragment mDelegate = null;
    private int mIcon = 0;
    private String mTitle = null;

    public ListUserBean(int itemType, int id, BaseBackFragment delegate, int icon, String title) {
        this.mItemType = itemType;
        this.mId = id;
        this.mDelegate = delegate;
        this.mIcon = icon;
        this.mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public int getIcon() {
        return mIcon;
    }

    public String getTitle() {
        return mTitle;
    }

    public BaseBackFragment getDelegate() {
        return mDelegate;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Builder {
        private int mItemType = 0;
        private int mId = 0;
        private BaseBackFragment mDelegate = null;
        private int mIcon = 0;
        private String mTitle = null;

        public Builder setItemType(int itemType) {
            this.mItemType = itemType;
            return this;
        }

        public Builder setId(int id) {
            this.mId = id;
            return this;
        }

        public Builder setDelegate(BaseBackFragment delegate) {
            this.mDelegate = delegate;
            return this;
        }

        public Builder setIcon(int icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public ListUserBean build() {
            return new ListUserBean(mItemType, mId, mDelegate, mIcon, mTitle);
        }
    }
}
