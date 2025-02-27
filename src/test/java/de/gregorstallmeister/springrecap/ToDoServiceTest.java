package de.gregorstallmeister.springrecap;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class ToDoServiceTest {

    ToDoRepository mockToDoRepository = mock(ToDoRepository.class);
    ToDoService toDoService = new ToDoService(mockToDoRepository);

    @Test
    void getToDoByIdExpectNoSuchElementExceptionWhenCalledWithNonExistingId() {
        assertThrows(NoSuchElementException.class, () -> {
            toDoService.getToDoByID("test1234NotExists");
        });
    }
}
