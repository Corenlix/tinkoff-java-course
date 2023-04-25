package ru.tinkoff.edu.java.scrapper.domain.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chat")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"links"})
public class JpaChatEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "subscription",
            joinColumns = @JoinColumn(name = "link_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id",
                    referencedColumnName = "id"))
    private List<JpaLinkEntity> links;
}
