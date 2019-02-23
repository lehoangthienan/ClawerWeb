/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2ee.webcrawler.models;

import org.json.simple.JSONObject;

/**
 *
 * @author An Le
 */
public class Image {
    private String id;
    private String name;
    private String url;
    private Integer storeInDay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getStoreInDay() {
        return storeInDay;
    }

    public void setStoreInDay(Integer storeInDay) {
        this.storeInDay = storeInDay;
    }
    
    public JSONObject toJSONObject() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", getId());
        jSONObject.put("name", getName());
        jSONObject.put("url", getUrl());
        return jSONObject;
    }
}
