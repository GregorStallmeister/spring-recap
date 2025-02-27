package de.gregorstallmeister.springrecap;

public record ToDoGetDto(
        String id,
        String description,
        String status
) {
}
