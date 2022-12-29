package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.ERoles;
import ra.model.entity.Roles;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Integer> {
    Optional<Roles> findByRoleName(ERoles rolesName);
}
