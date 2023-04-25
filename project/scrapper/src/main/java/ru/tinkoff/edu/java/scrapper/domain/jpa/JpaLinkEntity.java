package ru.tinkoff.edu.java.scrapper.domain.jpa;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "link")
@Data
public class JpaLinkEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "link_sec")
    @SequenceGenerator(name = "link_sec", sequenceName = "link_id_seq", allocationSize = 1)
    private Long id;

    @Column(name="url", unique = true)
    private String url;

    @Column(name="content_json", columnDefinition = "json")
    @Type(JsonBinaryType.class)
    private String contentJson;

    @Column(name="updated_at")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @ManyToMany
    @JoinTable(name="subscription",
            joinColumns = @JoinColumn(name = "link_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id",
                    referencedColumnName = "id"))
    private List<JpaChatEntity> chats = new ArrayList<>();
}