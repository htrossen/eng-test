package fitpay.engtest.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Token: Data model for serialization of a single credit card
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditCard implements Serializable {

    private String creditCardId;

    private String state;
}
