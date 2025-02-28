package de.gregorstallmeister.springrecap;

import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

@With
@Document
public record ToDo(
        String id,
        String description,
        Status status
) {

}
