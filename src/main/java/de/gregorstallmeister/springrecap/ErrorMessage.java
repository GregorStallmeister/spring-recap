package de.gregorstallmeister.springrecap;

import java.time.ZonedDateTime;

public record ErrorMessage(
        String errorMessage,
        ZonedDateTime timestamp
)
{
    }
