package com.raphaelcollin.ordermanagement.domain.event;

import com.raphaelcollin.ordermanagement.domain.entity.Destination;
import lombok.Value;

@Value
public class DestinationEvent {
    String address;
    double latitude;
    double longitude;
    String instructions;

    public static DestinationEvent fromEntity(Destination destination) {
        if (destination == null) {
            return null;
        }
        return new DestinationEvent(
                destination.getAddress(),
                destination.getLatitude(),
                destination.getLongitude(),
                destination.getInstructions()
        );
    }

    public Destination toEntity() {
        return new Destination(address, latitude, longitude, instructions);
    }
}
