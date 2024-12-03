package ib.develop.matstore.repositories;

import ib.develop.matstore.entities.OperationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationItemRepository extends JpaRepository<OperationItem,Long> {

}
