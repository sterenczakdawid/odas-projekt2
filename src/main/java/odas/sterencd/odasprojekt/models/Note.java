package odas.sterencd.odasprojekt.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Note {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "iv")
    private byte[] iv;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column
    private boolean isEncrypted;

    @Column
    private String username;
}
