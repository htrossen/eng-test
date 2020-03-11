package fitpay.engtest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Token: Data model for serialization of list of credit cards
 */
@Getter
@Setter
public class CreditCards implements Serializable {

    @JsonProperty("results")
    private List<CreditCard> cards;
}
