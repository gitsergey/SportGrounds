package com.example.sergey.sportgrounds.rest;


import android.util.Log;

import com.example.sergey.sportgrounds.model.Category;
import com.example.sergey.sportgrounds.model.Images;
import com.example.sergey.sportgrounds.model.Location;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import io.realm.RealmList;

public class LocationDeserializer implements JsonDeserializer<Location> {
    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Log.d("Test/Deserializer", "Using a custom deserializer");

        Gson gson = new Gson();

        Location location = gson.fromJson(json, Location.class);
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonArray jsonImagesArray = jsonObject.get("images").getAsJsonArray();

        RealmList<Images> images = new RealmList<>();
        for(int i = 1; i < jsonImagesArray.size(); i++) {
            Images image = gson.fromJson(jsonImagesArray.get(i), Images.class);
            images.add(image);
            //Log.d("Deserializer/Image URL", String.valueOf(image.getOriginUrl()));
            location.setImages(images);
        }

        final JsonElement jsonCategoryElement = jsonObject.get("category");
        Category category = gson.fromJson(jsonCategoryElement, Category.class);
        location.setCategory(category);

        return location;
    }
}
