package Entities.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Root {

        @JsonProperty("MyArray")
        public List<TriangleCreateResponse> getMyArray() {
            return this.myArray;
        }

        public void setMyArray(List<TriangleCreateResponse> myArray) {
            this.myArray = myArray;
        }

        List<TriangleCreateResponse> myArray;

}
