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

}
