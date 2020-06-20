package pl.coderslab.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
public class Advice extends GenericEntityID {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String guide;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updated;

    private Integer recommendations;
    private Integer shares;

    @ManyToOne
    private User admin;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "advice_tags", joinColumns = @JoinColumn(name = "advice_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @Column(name = "file_id")
    private Long fileId;

    @OneToOne
    private Training training;

    public Advice() {
        this.tags = new HashSet<>();
    }

    @PrePersist
    public void setCreated() {
        this.created = LocalDate.now();
    }

    @PreUpdate
    public void setUpdated() {
        this.updated = LocalDate.now();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public void setRecommendations(Integer recommendations) {
        this.recommendations = recommendations;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}