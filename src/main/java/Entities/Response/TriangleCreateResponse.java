package Entities.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TriangleCreateResponse {

    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    @JsonProperty("firstSide")
    public double getFirstSide() {
        return this.firstSide;
    }

    public void setFirstSide(double firstSide) {
        this.firstSide = firstSide;
    }

    double firstSide;

    @JsonProperty("secondSide")
    public double getSecondSide() {
        return this.secondSide;
    }

    public void setSecondSide(double secondSide) {
        this.secondSide = secondSide;
    }

    double secondSide;

    @JsonProperty("thirdSide")
    public double getThirdSide() {
        return this.thirdSide;
    }

    public void setThirdSide(double thirdSide) {
        this.thirdSide = thirdSide;
    }

    double thirdSide;
}




			