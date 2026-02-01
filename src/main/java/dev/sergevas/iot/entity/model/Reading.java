package dev.sergevas.iot.entity.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class Reading {

    @JsonbProperty("type")
    private String type;
    @JsonbProperty("data")
    private Number data;

    @JsonbCreator
    public Reading(@JsonbProperty("type") String type,
                   @JsonbProperty("data") double data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Number getData() {
        return data;
    }

    public void setData(Number data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Reading{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
