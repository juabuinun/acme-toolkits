package acme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import acme.entities.toolkit.Toolkit;

@Repository
public interface ToolkitRepository extends JpaRepository<Toolkit,Integer>,JpaSpecificationExecutor<Toolkit>{

}
