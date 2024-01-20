package odas.sterencd.odasprojekt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "iv")
    @JsonIgnore
    private byte[] iv;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column
    private boolean isEncrypted;

    @Column
    private String username;
}
