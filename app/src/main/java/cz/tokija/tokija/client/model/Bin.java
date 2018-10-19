package cz.tokija.tokija.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "firm_id",
        "number",
        "frequency",
        "placed",
        "taken",
        "shredded",
        "collect_date",
        "created_at",
        "updated_at",
        "url"
})
public class Bin {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("firm_id")
    private Integer firmId;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("frequency")
    private String frequency;
    @JsonProperty("placed")
    private String placed;
    @JsonProperty("taken")
    private String taken;
    @JsonProperty("shredded")
    private Object shredded;
    @JsonProperty("collect_date")
    private String collectDate;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("url")
    private String url;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("firm_id")
    public Integer getFirmId() {
        return firmId;
    }

    @JsonProperty("firm_id")
    public void setFirmId(Integer firmId) {
        this.firmId = firmId;
    }

    @JsonProperty("number")
    public Integer getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(Integer number) {
        this.number = number;
    }

    @JsonProperty("frequency")
    public String getFrequency() {
        return frequency;
    }

    @JsonProperty("frequency")
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @JsonProperty("placed")
    public String getPlaced() {
        return placed;
    }

    @JsonProperty("placed")
    public void setPlaced(String placed) {
        this.placed = placed;
    }

    @JsonProperty("taken")
    public String getTaken() {
        return taken;
    }

    @JsonProperty("taken")
    public void setTaken(String taken) {
        this.taken = taken;
    }

    @JsonProperty("shredded")
    public Object getShredded() {
        return shredded;
    }

    @JsonProperty("shredded")
    public void setShredded(Object shredded) {
        this.shredded = shredded;
    }

    @JsonProperty("collect_date")
    public String getCollectDate() {
        return collectDate;
    }

    @JsonProperty("collect_date")
    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

}