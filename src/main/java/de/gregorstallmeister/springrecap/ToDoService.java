package de.gregorstallmeister.springrecap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public List<ToDo> getToDos() {
        return toDoRepository.findAll();
    }

    public ToDo getToDoByID(String id) {
        Optional<ToDo> toDo = toDoRepository.findById(id);

        if (toDo.isPresent())
            return toDo.get();

        throw new NoSuchElementException("ToDo to get was not found with id " + id);
    }

    public ToDo insertToDo(String description, String status) {
        IdService idService = new IdService();
        ToDo toDo = new ToDo(idService.randomID(), description, status);
        toDoRepository.insert(toDo);

        return toDo;
    }

    public ToDo updateToDo(String id, String description, String status) {
        Optional<ToDo> toDoToUpdate = toDoRepository.findById(id);

        if (toDoToUpdate.isPresent()) {
            ToDo toDoUpdated = toDoToUpdate.get().withDescription(description).withStatus(status);
            toDoRepository.save(toDoUpdated);

            return toDoUpdated;
        }

        throw new NoSuchElementException("ToDo to update was not found with id " + id);
    }
}
