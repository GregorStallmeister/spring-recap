package de.gregorstallmeister.springrecap;

import java.util.ArrayList;
import java.util.List;

public class ToDoWrapper {

    static ToDoInteractDto wrapForInteracting(ToDo toDo) {
        return new ToDoInteractDto(toDo.id(), toDo.description(), toDo.status());
    }

    static List<ToDoInteractDto> wrapForInteracting(List<ToDo> toDoList) {
        List<ToDoInteractDto> toDoInteractDtoList = new ArrayList<>();

        for (ToDo toDo : toDoList) {
            ToDoInteractDto toDoInteractDto = wrapForInteracting(toDo);
            toDoInteractDtoList.add(toDoInteractDto);
        }

        return toDoInteractDtoList;
    }
}
