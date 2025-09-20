package com.seidelsoft.ERPBackend.menu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seidelsoft.ERPBackend.authorization.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO implements Serializable {

    private Long id;
    private String name;
    private String url;
    private String icon;
    private Boolean active;
    private Boolean homePage;
    private Integer orderPosition;
    private Permission permission;
    private MenuDTO parent;
    private List<MenuDTO> children;

    @JsonIgnore
    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    @JsonIgnore
    public boolean isRoot() {
        return parent == null;
    }

    @JsonIgnore
    public List<MenuDTO> getActiveChildren() {
        if (children == null) {
            return new ArrayList<>();
        }
        return children.stream()
                .filter(child -> Boolean.TRUE.equals(child.getActive()))
                .toList();
    }
}

