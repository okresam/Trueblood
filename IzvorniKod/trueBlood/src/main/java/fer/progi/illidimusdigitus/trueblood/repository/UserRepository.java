package fer.progi.illidimusdigitus.trueblood.repository;

import fer.progi.illidimusdigitus.trueblood.model.Blood;
import fer.progi.illidimusdigitus.trueblood.model.Role;
import fer.progi.illidimusdigitus.trueblood.model.User;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.username = ?1 AND u.password = ?2 ")
    Optional<User> getUsernamePassword(String username, String password);

    Optional<User> findByUsername(String username);

    Optional<User> findByOib(String oib);

    Optional<User> findByEmail(String email);

    Optional<User> findByActivation(String activation);

    
    List<User> findByBloodType(Blood blood);
    
    List<User> findByRole(Role role);

    void deleteUserByUsername(String username);
}
