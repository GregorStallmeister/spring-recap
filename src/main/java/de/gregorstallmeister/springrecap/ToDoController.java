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
    public List<ToDoGetDto> getToDosBoard() {
        return toDoService.getToDos();
    }

    @GetMapping("/api/todo")
    public List<ToDoGetDto> getToDosApi() {
        return toDoService.getToDos();
    }

    @GetMapping("/api/todo/{id}")
    public ToDoGetDto getTodoByID(@PathVariable String id) {
        return ToDoWrapper.wrapForGet(toDoService.getToDoByID(id));
    }

    @PostMapping("/api/todo")
    public ToDoGetDto insertToDo(@RequestBody ToDoPostDto toDoPostDto) {
        ToDo toDo;
        toDo = toDoService.insertToDo(toDoPostDto.description(), toDoPostDto.status());

        return ToDoWrapper.wrapForGet(toDo);
    }
}
