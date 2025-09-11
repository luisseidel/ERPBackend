package com.seidelsoft.ERPBackend.auth.service;

import com.seidelsoft.ERPBackend.auth.model.entity.Role;
import com.seidelsoft.ERPBackend.auth.model.enumerations.RoleEnum;
import com.seidelsoft.ERPBackend.auth.repository.RoleRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role, RoleRepository> {

    @Override
    public boolean validar(Role entity, StringBuilder msgValidacao) {
        if (entity != null) {
            if (RoleEnum.ADMIN.getValue().compareTo(entity.getId()) == 0) {
                msgValidacao.append("Não é possível excluir a Role de Admin");
            }
            if (RoleEnum.USER.getValue().compareTo(entity.getId()) != 0) {
                msgValidacao.append("Não é possível excluir a Role User");
            }
        }
        return true;
    }

}
