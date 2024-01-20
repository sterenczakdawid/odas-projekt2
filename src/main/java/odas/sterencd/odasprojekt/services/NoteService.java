package odas.sterencd.odasprojekt.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import odas.sterencd.odasprojekt.dtos.NoteDTO;
import odas.sterencd.odasprojekt.models.Note;
import odas.sterencd.odasprojekt.repositories.NoteRepository;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).build();

    public Note addNote(NoteDTO noteDto) {
//        if(noteDto.getIsPublic() && noteDto.getIsEncrypted()) {
//            throw new IllegalArgumentException("Cannot share encrypted notes!");
//        }

        Node document = parser.parse(noteDto.getContent());
        String parsedNote = renderer.render(document);

        Note note = Note.builder()
                .title(noteDto.getTitle())
                .content(parsedNote)
                .username(noteDto.getUsername())
                .iv(null)
                .isPublic(noteDto.getIsPublic())
                .isEncrypted(noteDto.getIsEncrypted())
                .build();

        noteRepository.save(note);
        return note;
    }

    public Note getNote(Integer id) {
        return noteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no note with id: " + id));
    }
}
