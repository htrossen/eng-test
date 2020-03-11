package fitpay.engtest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Token: Data model for serialization of list of devices
 */
@Getter
@Setter
public class Devices implements Serializable {

    @JsonProperty("results")
    private List<Device> devices;
}
