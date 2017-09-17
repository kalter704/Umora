package ru.comp.vas.umora;

import android.os.Bundle;

public class Source {

    private static final String SITE_BUNDLE = "site_bundle";
    private static final String NAME_BUNDLE = "name_bundle";
    private static final String DESC_BUNDLE = "desc_bundle";

    private String mSite;
    private String mName;
    private String mDesc;

    public Source(String site, String name, String desc) {
        mSite = site;
        mName = name;
        mDesc = desc;
    }

    public Source(Bundle bundle) {
        mSite = bundle.getString(SITE_BUNDLE);
        mName = bundle.getString(NAME_BUNDLE);
        mDesc = bundle.getString(DESC_BUNDLE);
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        mSite = site;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public Bundle getBundle() {
        Bundle b = new Bundle();
        b.putString(SITE_BUNDLE, mSite);
        b.putString(NAME_BUNDLE, mName);
        b.putString(DESC_BUNDLE, mDesc);
        return b;
    }
}
