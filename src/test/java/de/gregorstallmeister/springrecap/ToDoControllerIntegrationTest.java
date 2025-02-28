package de.gregorstallmeister.springrecap;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ToDoControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ToDoRepository toDoRepository;

    @Test
    @DirtiesContext
    void getToDosBoard() throws Exception {
        // given
        IdService idService = new IdService();
        String id = idService.randomID();
        ToDo toDo = new ToDo(id, "My test description", Status.OPEN);
        toDoRepository.save(toDo);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/board/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                          {
                            "description": "My test description",
                            "status": "OPEN"
                          }
                        ]
                        """));
//                .andExpect(jsonPath("$.id").isNotEmpty()); // does not work, cause got an array, not a single object
    }

    @Test
    @DirtiesContext
    void getToDosApi() throws Exception {
        // given
        IdService idService = new IdService();
        String id = idService.randomID();
        ToDo toDo = new ToDo(id, "testDescription", Status.OPEN);
        toDoRepository.insert(toDo);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                          {
                            "description": "testDescription",
                            "status": "OPEN"
                          }
                        ]
                        """));
    }

    @Test
    @DirtiesContext
    void insertToDo() throws Exception {
        // given: Nothing but the class members

        // when + then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                    "description": "myFurtherTest",
                                    "status": "OPEN"
                                  }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                          "description": "myFurtherTest",
                          "status": "OPEN"
                        }
                        """))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DirtiesContext
    void getToDoById() throws Exception {
        // given
        IdService idService = new IdService();
        String id = idService.randomID();
        ToDo toDo = new ToDo(id, "anotherDescription", Status.OPEN);
        toDoRepository.insert(toDo);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                          "description": "anotherDescription",
                          "status": "OPEN"
                        }
                        """))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @DirtiesContext
    void getToDoByIDExpectExceptionWithNonExistingId() {
        assertThrows(ServletException.class, () -> {
            // backend method throws NoSuchElementException. Framework catches it and throws jakarta.servlet.ServletException.
           mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/test1234"))
                   .andExpect(MockMvcResultMatchers.status().isOk());
        });
    }

    @Test
    @DirtiesContext
    void getToDoByIDExpectExceptionWithNonExistingIdCatchException() {
        try
        {
            // backend method throws NoSuchElementException. Framework catches it and throws jakarta.servlet.ServletException.
            mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/test1234"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
        catch (Exception ex) {
            assertTrue(ex.getCause().toString().matches(".*NoSuchElementException.*"));

            NoSuchElementException causeEx = (NoSuchElementException) ex.getCause();
            System.out.println(causeEx.toString());
        }
    }
}
