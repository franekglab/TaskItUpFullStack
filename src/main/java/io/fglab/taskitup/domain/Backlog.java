package io.fglab.taskitup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "backlog")
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer tlSequence = 0;
    private String listIdentifier;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taskList_id", nullable = false)
    @JsonIgnore
    private TaskList taskList;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "backlog", orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public Backlog() {

    }
}
