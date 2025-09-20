package com.seidelsoft.ERPBackend.authorization.service;

import com.seidelsoft.ERPBackend.authentication.model.enumerations.RoleEnum;
import com.seidelsoft.ERPBackend.authorization.entity.Role;
import com.seidelsoft.ERPBackend.authorization.repository.RoleRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role, RoleRepository> {

    @Override
    public boolean validar(Role entity, StringBuilder msgValidacao) {
        if (entity != null && entity.getId() != null) {
            if (RoleEnum.ADMIN.getValue().compareTo(entity.getId()) == 0) {
                msgValidacao.append("Não é possível editar ou excluir a Role de Admin");
            }
            if (RoleEnum.USER.getValue().compareTo(entity.getId()) == 0) {
                msgValidacao.append("Não é possível editar ou excluir a Role User");
            }
        }
        return msgValidacao.isEmpty();
    }

}
