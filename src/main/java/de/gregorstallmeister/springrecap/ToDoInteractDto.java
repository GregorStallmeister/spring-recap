package de.gregorstallmeister.springrecap;

public record ToDoInteractDto(
        String id,
        String description,
        String status
) {
}
