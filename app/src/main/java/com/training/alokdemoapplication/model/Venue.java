package com.training.alokdemoapplication.model;

import com.google.gson.annotations.SerializedName;

public class Venue {
    @SerializedName("ground")
    private String ground;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;

    public String getGround() { return ground == null ? "" : ground; }
    public String getCity() { return city == null ? "" : city; }
    public String getCountry() { return country == null ? "" : country; }

    public void setGround(String v) { this.ground = v; }
    public void setCity(String v) { this.city = v; }
    public void setCountry(String v) { this.country = v; }

    public String getDisplayVenue() {
        StringBuilder sb = new StringBuilder();
        if (!getGround().isEmpty()) sb.append(getGround());
        if (!getCity().isEmpty()) sb.append(sb.length() > 0 ? ", " : "").append(getCity());
        if (!getCountry().isEmpty()) sb.append(sb.length() > 0 ? ", " : "").append(getCountry());
        return sb.length() == 0 ? "Venue TBA" : sb.toString();
    }
}
