package odas.sterencd.odasprojekt.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odas.sterencd.odasprojekt.dtos.NoteDTO;
import odas.sterencd.odasprojekt.dtos.NoteGetDTO;
import odas.sterencd.odasprojekt.models.Note;
import odas.sterencd.odasprojekt.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/notes")
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/public")
    public List<Note> getPublicNotes() {
        return noteService.getPublicNotes();
    }

    @GetMapping("/my")
    public List<Note> getUsersNotes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return noteService.getNotesByUsername(authentication.getName());
    }

    @PostMapping("/add")
    public Note saveNote(@RequestBody NoteDTO noteDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return noteService.addNote(noteDto, username);
    }

    @PostMapping("/details")
    public Note noteDetails(@RequestBody NoteGetDTO noteGetDto) throws Exception {
        Note note = noteService.getNote(noteGetDto.getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(!note.isPublic() && !username.equals(note.getUsername())) {
            throw new IllegalArgumentException("Nie masz dostępu do tej notatki!");
        }
        if(noteGetDto.getPassword() != null){
            return noteService.getDecryptedNote(note.getId(),noteGetDto.getPassword());
        } else {
            return note;
        }
    }

}
