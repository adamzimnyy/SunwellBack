package app.repository;

import app.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by adamz on 26.01.2018.
 */

@Repository
public interface ItemRepository  extends JpaRepository<Item,Integer >{
    Item findById(int id);
}
