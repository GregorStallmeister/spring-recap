package de.gregorstallmeister.springrecap;

public class ToDoWrapper {

    static ToDoGetDto wrapForGet(ToDo toDo) {
        return new ToDoGetDto(toDo.id(), toDo.description(), toDo.state());
    }
}
