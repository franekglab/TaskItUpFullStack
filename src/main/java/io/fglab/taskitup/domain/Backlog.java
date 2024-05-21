package io.fglab.taskitup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "backlog")
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer tlSequence = 0;
    private String listIdentifier;

    //One to one with tasklist

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taskList_id", nullable = false)
    @JsonIgnore
    private TaskList taskList;

    //One to many tasks under list

    public Backlog() {

    }
}
