package acme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.entities.UserAccount;
import acme.roles.Inventor;

@Repository
public interface InventorRepository extends GenericJpaRepository<Inventor>{

	@Query("select p from Inventor p where p.userAccount.id = :id")
	Inventor findOneInventorByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);
}
