package com.raphaelcollin.ordermanagement.application.ordermanager.response;

import com.raphaelcollin.ordermanagement.domain.entity.Destination;
import lombok.Value;

@Value
public class DestinationResponse {
    String address;
    double latitude;
    double longitude;
    String instructions;

    public static DestinationResponse fromDomain(Destination destination) {
        if (destination == null) {
            return null;
        }
        return new DestinationResponse(
                destination.getAddress(),
                destination.getLatitude(),
                destination.getLongitude(),
                destination.getInstructions()
        );
    }
}
