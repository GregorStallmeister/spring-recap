package de.gregorstallmeister.springrecap;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

@With
@Document
public record ToDo(
        String id,
        String description,
        String status
) {

}
