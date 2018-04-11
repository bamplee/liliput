package me.nexters.liliput.api.infrastructure.persistence.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "SHORT_URL")
public class ShortUrl extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8295688402595799135L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private SocialUser user;
    private String path;
    private String webUrl;
    private Boolean isActive;
}
