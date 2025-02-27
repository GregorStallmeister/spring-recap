package de.gregorstallmeister.springrecap;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoRepository extends MongoRepository<ToDo, String> {
    Optional<ToDo> findById(String id);
}
