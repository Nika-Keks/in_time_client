package com.example.client_in_time.api_in_time.apitypes;

import com.google.gson.annotations.SerializedName;


public class Dish {
    public int id;
    @SerializedName("updateTime")
    public String updateTime;
    @SerializedName("image_path")
    public String imagePath;
    @SerializedName("restaurant_id")
    public String restaurantId;
    public String status;
    public int price;
    public String description;
    @SerializedName("create_time")
    public String createTime;
    public String name;
}
