package de.gregorstallmeister.springrecap;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping() // leave empty, cause some frontend requests go to "/api", some to "/board"
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/board/todo")
    public List<ToDoInteractDto> getToDosBoard() {
        return ToDoWrapper.wrapForInteracting(toDoService.getToDos());
    }

    @GetMapping("/api/todo")
    public List<ToDoInteractDto> getToDosApi() {
        return ToDoWrapper.wrapForInteracting(toDoService.getToDos());
    }

    @GetMapping("/api/todo/{id}")
    public ToDoInteractDto getTodoByID(@PathVariable String id) {
        return ToDoWrapper.wrapForInteracting(toDoService.getToDoByID(id));
    }

    @PostMapping("/api/todo")
    public ToDoInteractDto insertToDo(@RequestBody ToDoInsertDto toDoInsertDto) {
        ToDo toDo;
        toDo = toDoService.insertToDo(toDoInsertDto.description(), toDoInsertDto.status());

        return ToDoWrapper.wrapForInteracting(toDo);
    }

    @PutMapping ("/api/todo/{id}")
    public ToDoInteractDto updateToDo(@RequestBody ToDoInteractDto toDoInteractDto) {
        ToDo updatedToDo = toDoService.updateToDo(toDoInteractDto.id(), toDoInteractDto.description(), toDoInteractDto.status());

        return ToDoWrapper.wrapForInteracting(updatedToDo);
    }

    @DeleteMapping("/api/todo/{id}")
    public void deleteToDo(@PathVariable String id) {
        toDoService.deleteToDo(id);
    }
}
