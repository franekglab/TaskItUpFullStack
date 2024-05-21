package io.fglab.taskitup.repositories;

import io.fglab.taskitup.domain.Backlog;
import org.springframework.data.repository.CrudRepository;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findByListIdentifier(String Identifier);
}
