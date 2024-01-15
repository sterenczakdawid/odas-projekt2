package odas.sterencd.odasprojekt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {
    @GetMapping
    public String whatever() {
        return "Working shit";
    }
}
