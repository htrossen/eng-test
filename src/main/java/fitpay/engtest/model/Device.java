package fitpay.engtest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Token: Data model for serialization of a single device
 */
@Getter
@Setter
@NoArgsConstructor
public class Device implements Serializable {

    private String deviceId;

    private String state;

    @JsonProperty("deviceIdentifier")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @JsonProperty("deviceId")
    public String getDeviceId() {
        return deviceId;
    }
}