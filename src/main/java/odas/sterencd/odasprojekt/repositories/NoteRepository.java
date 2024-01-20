package odas.sterencd.odasprojekt.repositories;

import odas.sterencd.odasprojekt.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
