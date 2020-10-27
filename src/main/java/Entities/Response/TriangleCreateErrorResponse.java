package Entities.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TriangleCreateErrorResponse {

        @JsonProperty("timestamp")
        public long getTimestamp() {
            return this.timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        long timestamp;

        @JsonProperty("status")
        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        int status;

        @JsonProperty("error")
        public String getError() {
            return this.error;
        }

        public void setError(String error) {
            this.error = error;
        }

        String error;

        @JsonProperty("exception")
        public String getException() {
            return this.exception;
        }

        public void setException(String exception) {
            this.exception = exception;
        }

        String exception;

        @JsonProperty("message")
        public String getMessage() {
            return this.message;
        }



        public void setMessage(String message) {
            this.message = message;
        }

        String message;

        @JsonProperty("path")
        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        String path;

}
			