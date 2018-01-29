package app.repository;

import app.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface CharacterRepository extends JpaRepository<Character,String> {

    Character findByName(String name);

    @Modifying
    @Transactional
        // Make sure to import org.springframework.transaction.annotation.Transactional
     int deleteByLastUpdatedBefore(Date expiryDate);
}
