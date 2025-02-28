package de.gregorstallmeister.springrecap;

public record ToDoInsertDto(
        String description,
        Status status
) {
}
