package me.nexters.liliput.api.domain.shorten.repository;

import lombok.Data;
import me.nexters.liliput.api.domain.shared.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String path;
    private String webUrl;
    private Boolean isActive;
}
