package fitpay.engtest.controller;


import fitpay.engtest.model.Response;
import fitpay.engtest.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller: Exposes endpoints to clients.
 */
@org.springframework.stereotype.Controller
@RequestMapping(value = "/compositeUsers", produces = APPLICATION_JSON_VALUE)
public class Controller {

    @Autowired
    Service service;

    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getUserInfo(
            @PathVariable String userId,
            @RequestParam(required = false) String creditCardState,
            @RequestParam(required = false) String deviceState)
    {

        Response response = service.getUserInfo(userId);

        if (creditCardState != null && deviceState != null) {
            response = service.getUserInfoCardDeviceFilter(response, creditCardState, deviceState);
        } else if (creditCardState != null) {
            response = service.getUserInfoCardFilter(response, creditCardState);
        } else if (deviceState != null){
            response = service.getUserInfoDeviceFilter(response, deviceState);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
