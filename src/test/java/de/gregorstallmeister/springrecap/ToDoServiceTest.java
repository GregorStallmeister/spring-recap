package de.gregorstallmeister.springrecap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ToDoServiceTest {

    ToDoRepository mockToDoRepository = mock(ToDoRepository.class);
    ToDoService toDoService = new ToDoService(mockToDoRepository);

    @BeforeEach
    void clear() {
        mockToDoRepository.deleteAll(); // this is useless, cause a mocked repository will not save anything at all
    }

    @Test
    void getToDoByIdExpectNoSuchElementExceptionWhenCalledWithNonExistingId() {
        String id = "test1234NotExists";

        assertThrows(NoSuchElementException.class, () -> toDoService.getToDoByID(id));
        verify(mockToDoRepository).findById(id);
    }

    @Test
    void insertToDo() {
        // given
        String description = "myTest";
        IdService idService = new IdService();
        String id = idService.randomID();
        ToDo toDoMocked = new ToDo(id, description, Status.OPEN);
        when(mockToDoRepository.insert(toDoMocked)).thenReturn(toDoMocked);

        // when
        ToDo toDoInserted = toDoService.insertToDo(description, Status.OPEN);

        // then
        verify(mockToDoRepository).insert(toDoInserted);
        assertNotNull(toDoInserted);
        assertEquals(toDoMocked.description(), toDoInserted.description());
        assertEquals(toDoMocked.status(), toDoInserted.status());
        assertNotNull(toDoInserted.id());
    }

    @Test
    void updateToDo() {
        // given
        ToDo toDoFresh = toDoService.insertToDo("myTest", Status.OPEN);
        ToDo toDoUpdatedExpected = toDoFresh.withDescription("myTestUpdated").withStatus(Status.IN_PROGRESS);
        when(mockToDoRepository.findById(toDoFresh.id())).thenReturn(Optional.of(toDoFresh));
        when(mockToDoRepository.save(toDoUpdatedExpected)).thenReturn(toDoUpdatedExpected);

        // when
        ToDo toDoUpdatedActual = toDoService.updateToDo(toDoFresh.id(), "myTestUpdated", Status.IN_PROGRESS);

        // then
        verify(mockToDoRepository).findById(toDoFresh.id());
        verify(mockToDoRepository).save(toDoUpdatedActual);
        assertEquals(toDoUpdatedExpected, toDoUpdatedActual);
    }
}
