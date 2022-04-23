package acme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import acme.entities.configuration.SpamWord;

public interface SpamWordRepository extends JpaRepository<SpamWord,Integer>{

}
