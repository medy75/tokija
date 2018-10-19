package cz.tokija.tokija.client.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.Date;


public class Bin {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("firm_name")
    @Expose
    private String firmName;
    @SerializedName("firm_id")
    @Expose
    private Integer firmId;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("frequency")
    @Expose
    private String frequency;
    @SerializedName("placed")
    @Expose
    private Date placed;
    @SerializedName("taken")
    @Expose
    private Date taken;
    @SerializedName("shredded")
    @Expose
    private Date shredded;
    @SerializedName("collect_date")
    @Expose
    private Date collectDate;
    @SerializedName("created_at")
    @Expose
    private Date createdAt;
    @SerializedName("updated_at")
    @Expose
    private Date updatedAt;
    @SerializedName("url")
    @Expose
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public Integer getFirmId() {
        return firmId;
    }

    public void setFirmId(Integer firmId) {
        this.firmId = firmId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public DateTime getPlaced() {
        return new DateTime(placed);
    }

    public void setPlaced(Date placed) {
        this.placed = placed;
    }

    public DateTime getTaken() {
        return new DateTime(taken);
    }

    public void setTaken(Date taken) {
        this.taken = taken;
    }

    public DateTime getShredded() {
        return new DateTime(shredded);
    }

    public void setShredded(Date shredded) {
        this.shredded = shredded;
    }

    public DateTime getCollectDate() {
        return new DateTime(collectDate);
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    public DateTime getCreatedAt() {
        return new DateTime(createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getUpdatedAt() {
        return new DateTime(updatedAt);
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}