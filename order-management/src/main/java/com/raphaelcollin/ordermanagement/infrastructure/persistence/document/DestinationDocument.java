package com.raphaelcollin.ordermanagement.infrastructure.persistence.document;

import com.raphaelcollin.ordermanagement.domain.entity.Destination;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class DestinationDocument {
    String address;
    double latitude;
    double longitude;
    String instructions;

    public Destination toDomain() {
        return new Destination(address, latitude, longitude, instructions);
    }

    public static DestinationDocument fromDomain(Destination destination) {
        return new DestinationDocument(
                destination.getAddress(),
                destination.getLatitude(),
                destination.getLongitude(),
                destination.getInstructions()
        );
    }
}
