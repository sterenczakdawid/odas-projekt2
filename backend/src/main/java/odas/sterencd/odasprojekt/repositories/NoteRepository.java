package odas.sterencd.odasprojekt.repositories;

import odas.sterencd.odasprojekt.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    public List<Note> findByIsPublicTrue();
    public List<Note> findByUsername(String username);
}
