/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos;


import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.JSON;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String url;
    private LocalDateTime updatedAt;
    private JSON contentJson;

    public Link() {}

    public Link(Link value) {
        this.id = value.id;
        this.url = value.url;
        this.updatedAt = value.updatedAt;
        this.contentJson = value.contentJson;
    }

    @ConstructorProperties({ "id", "url", "updatedAt", "contentJson" })
    public Link(
        @NotNull Long id,
        @NotNull String url,
        @NotNull LocalDateTime updatedAt,
        @Nullable JSON contentJson
    ) {
        this.id = id;
        this.url = url;
        this.updatedAt = updatedAt;
        this.contentJson = contentJson;
    }

    /**
     * Getter for <code>public.link.id</code>.
     */
    @NotNull
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>public.link.id</code>.
     */
    public void setId(@NotNull Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>public.link.url</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>public.link.url</code>.
     */
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    /**
     * Getter for <code>public.link.updated_at</code>.
     */
    @NotNull
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Setter for <code>public.link.updated_at</code>.
     */
    public void setUpdatedAt(@NotNull LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Getter for <code>public.link.content_json</code>.
     */
    @Nullable
    public JSON getContentJson() {
        return this.contentJson;
    }

    /**
     * Setter for <code>public.link.content_json</code>.
     */
    public void setContentJson(@Nullable JSON contentJson) {
        this.contentJson = contentJson;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Link other = (Link) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.url == null) {
            if (other.url != null)
                return false;
        }
        else if (!this.url.equals(other.url))
            return false;
        if (this.updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        }
        else if (!this.updatedAt.equals(other.updatedAt))
            return false;
        if (this.contentJson == null) {
            if (other.contentJson != null)
                return false;
        }
        else if (!this.contentJson.equals(other.contentJson))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode());
        result = prime * result + ((this.contentJson == null) ? 0 : this.contentJson.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Link (");

        sb.append(id);
        sb.append(", ").append(url);
        sb.append(", ").append(updatedAt);
        sb.append(", ").append(contentJson);

        sb.append(")");
        return sb.toString();
    }
}