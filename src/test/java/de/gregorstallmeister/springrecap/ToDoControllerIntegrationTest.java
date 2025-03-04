package de.gregorstallmeister.springrecap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    void getToDosBoard() {
        // given
        IdService idService = new IdService();
        String id = idService.randomID();
        ToDo toDo = new ToDo(id, "My test description", Status.OPEN);
        toDoRepository.save(toDo);

        try {
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
//                .andExpect(jsonPath("$.id").isNotEmpty()); // does not work, cause got not a single object
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getToDosApi() {
        // given
        IdService idService = new IdService();
        String id = idService.randomID();
        ToDo toDo = new ToDo(id, "testDescription", Status.OPEN);
        toDoRepository.insert(toDo);

        try {
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
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void insertToDo() {
        // given: Nothing but the class members

        try {
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
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            
                                 {
                               "description": "myFurtherTest",
                               "status": "OPEN"
                             }
                            
                            """))
                    .andExpect(jsonPath("$.id").isNotEmpty());
        }
        catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getToDoById() {
        // given
        IdService idService = new IdService();
        String id = idService.randomID();
        ToDo toDo = new ToDo(id, "anotherDescription", Status.OPEN);
        toDoRepository.insert(toDo);

        try {
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
        catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getToDoByIDExpectNotFoundAndErrorMessageWhenCalledWithNonExistingId() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/test1234"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isNotEmpty());
        } catch (Exception ex) {
            Assertions.fail();
        }
    }
}
