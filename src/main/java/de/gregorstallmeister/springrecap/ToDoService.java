package de.gregorstallmeister.springrecap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public List<ToDoGetDto> getToDos() {
        List<ToDo> toDoList = toDoRepository.findAll();
        List<ToDoGetDto> toDoGetDtoList = new ArrayList<>();

        for (ToDo toDo : toDoList) {
            ToDoGetDto toDoGetDto = ToDoWrapper.wrapForGet(toDo);
            toDoGetDtoList.add(toDoGetDto);
        }

        return toDoGetDtoList;
    }

    public ToDo getToDoByID (String id) {
        Optional<ToDo> toDo = toDoRepository.findById(id);

        if (toDo.isPresent())
            return toDo.get();

        throw new NoSuchElementException("ToDo was not found with id " + id);
    }

    public ToDo insertToDo(String description, String status) {
        IdService idService = new IdService();
        ToDo toDo = new ToDo(idService.randomID(), description, status);
        toDoRepository.insert(toDo);

        return toDo;
    }
}
