package acme.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.entities.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Integer>,JpaSpecificationExecutor<UserAccount> {	
	
	@Query("select u from UserAccount u where u.enabled=1 and Administrator not in (select type(r) from u.roles r)")
	List<UserAccount> findAllEnabled();
}
