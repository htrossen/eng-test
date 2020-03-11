package fitpay.engtest.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Token: Data model for serialization of bearer token
 */
@Getter
@Setter
public class Token implements Serializable {

    private String access_token;
}
