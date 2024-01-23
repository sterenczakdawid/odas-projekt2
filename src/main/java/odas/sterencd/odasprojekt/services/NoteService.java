package odas.sterencd.odasprojekt.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odas.sterencd.odasprojekt.dtos.NoteDTO;
import odas.sterencd.odasprojekt.models.Note;
import odas.sterencd.odasprojekt.repositories.NoteRepository;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {
    private final NoteRepository noteRepository;

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).build();
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public Note addNote(NoteDTO noteDto, String username) {
        if(noteDto.getIsPublic() && noteDto.getIsEncrypted()) {
            throw new IllegalArgumentException("Cannot share encrypted notes!");
        }

        Node document = parser.parse(noteDto.getContent());
        String parsedNote = renderer.render(document);

        byte[] iv = {};
        if(noteDto.getIsEncrypted()) {
            try {
                SecretKey key = getKey(noteDto.getPassword());
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                IvParameterSpec ivSpec = generateIv();
                cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
                byte[] cipherText = cipher.doFinal(noteDto.getContent().getBytes());
                iv = ivSpec.getIV();
//                text = Base64.getEncoder().encodeToString(cipherText);

                Note note = Note.builder()
                        .title(noteDto.getTitle())
                        .content(Base64.getEncoder().encodeToString(cipherText))
                        .username(username)
                        .iv(iv)
                        .isPublic(noteDto.getIsPublic())
                        .isEncrypted(noteDto.getIsEncrypted())
                        .build();
                noteRepository.save(note);
                return note;
            } catch(Exception e){}
        }
        Note note = Note.builder()
                .title(noteDto.getTitle())
                .content(parsedNote)
                .username(username)
                .iv(null)
                .isPublic(noteDto.getIsPublic())
                .isEncrypted(noteDto.getIsEncrypted())
                .build();

        noteRepository.save(note);
        return note;
    }

    public Note getDecryptedNote(Integer id, String password){
        Note note =  getNote(id);
        if(password ==null || note.getIv().length ==0){
            throw new IllegalStateException("No encrypted note");
        }
        try{
            String encryptedText =note.getContent();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getKey(password), new IvParameterSpec(note.getIv()));
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                    .decode(encryptedText));

            Node document = parser.parse(new String(plainText));
            String parsedNote = renderer.render(document);

            note.setContent(parsedNote);
            note.setEncrypted(false);
            return note;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during encryption/decryption.", e);
        }
    }

    private SecretKey getKey(String password) throws Exception{
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), "1234".getBytes(), 65536, 256);
        return new SecretKeySpec(keyFactory.generateSecret(keySpec).getEncoded(), "AES");
    }

    public Note getNote(Integer id) {
        return noteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no note with id: " + id));
    }

    public List<Note> getPublicNotes() {
        return noteRepository.findByIsPublicTrue();
    }

    public List<Note> getNotesByUsername(String username) {
        return noteRepository.findByUsername(username);
    }

    private static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

}
