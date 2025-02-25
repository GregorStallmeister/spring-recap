package de.gregorstallmeister.springrecap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
