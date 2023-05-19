package com.example.client_in_time.api_in_time.apitypes;

import com.google.gson.annotations.SerializedName;

public class Restaurant {
    public int id;
    public String description;
    public String phone;

    @SerializedName("update_time")
    public String updateTime;
    @SerializedName("create_time")
    public String createTime;
    @SerializedName("user_id")
    public int userId;
    @SerializedName("image_path")
    public String imagePath;
    @SerializedName("wday_opening")
    public String wdayOpen;
    @SerializedName("wday_closing")
    public String WdayClose;
    @SerializedName("wend_opening")
    public String wendOpen;
    @SerializedName("wend_closing")
    public String wendClose;
}
