package fitpay.engtest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Response: Data model for client response
 */
@Getter
@Setter
@NoArgsConstructor
public class Response implements Serializable {

    private String userId;

    private List<CreditCard> creditCards;

    private List<Device> devices;
}
