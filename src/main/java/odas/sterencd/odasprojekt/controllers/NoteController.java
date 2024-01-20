package odas.sterencd.odasprojekt.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import odas.sterencd.odasprojekt.dtos.NoteDTO;
import odas.sterencd.odasprojekt.models.Note;
import odas.sterencd.odasprojekt.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/add")
    public Note saveNote(@RequestBody NoteDTO noteDto){
        return noteService.addNote(noteDto);
    }
    @GetMapping("/details/{id}")
    public Note noteDetails(@PathVariable Integer id) throws Exception {
        Note note;
        try{
            note = noteService.getNote(id);
            return note;
        } catch ( EntityNotFoundException e) {
            return null;
        }
    }

}
