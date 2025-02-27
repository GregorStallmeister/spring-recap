package de.gregorstallmeister.springrecap;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdService {
    public String randomID() {
        return UUID.randomUUID().toString();
    }
}
