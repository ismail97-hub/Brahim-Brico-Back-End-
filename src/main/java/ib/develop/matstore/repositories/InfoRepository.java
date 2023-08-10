package ib.develop.matstore.repositories;

import ib.develop.matstore.entities.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoRepository extends JpaRepository<Info,Long> {
}
