package fer.progi.illidimusdigitus.trueblood.service;

import fer.progi.illidimusdigitus.trueblood.model.Role;
import fer.progi.illidimusdigitus.trueblood.model.util.RoleName;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findByName(RoleName name);
}
