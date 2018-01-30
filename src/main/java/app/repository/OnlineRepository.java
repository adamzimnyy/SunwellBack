package app.repository;

import app.model.Character;
import app.model.Online;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface OnlineRepository extends JpaRepository<Online,Integer> {

   int deleteByDateBefore(Date date);

}
