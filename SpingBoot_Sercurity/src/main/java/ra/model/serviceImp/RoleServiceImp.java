package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.ERoles;
import ra.model.entity.Roles;
import ra.model.repository.RolesRepository;
import ra.model.service.RoleService;

import java.util.Optional;
@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public Optional<Roles> findByRoleName(ERoles roleName) {
        return rolesRepository.findByRoleName(roleName);
    }
}
