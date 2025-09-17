package com.seidelsoft.ERPBackend.menu.model;

import com.seidelsoft.ERPBackend.authorization.entity.Permission;
import com.seidelsoft.ERPBackend.system.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu")
@SequenceGenerator(name = "menu_generator", sequenceName = "seq_menu", allocationSize = 1)
public class Menu extends BaseEntity {

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_generator")
    public Long getId() {
        return super.getId();
    }

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "url", length = 255, nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private Menu parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("orderPosition ASC")
    @Builder.Default
    @ToString.Exclude
    private List<Menu> children = new ArrayList<>();

    @Column(name = "order_position")
    private Integer orderPosition;

    @Column(name = "active")
    @Builder.Default
    private Boolean active = true;

    @Column(name = "home_page")
    @Builder.Default
    private Boolean homePage = false;

    @Column(name = "icon", length = 50)
    private String icon;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    /**
     * Verifica se este menu tem filhos
     */
    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    /**
     * Verifica se este é um menu raiz (sem pai)
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * Retorna apenas os filhos ativos
     */
    public List<Menu> getActiveChildren() {
        if (children == null) {
            return new ArrayList<>();
        }
        return children.stream()
                .filter(child -> Boolean.TRUE.equals(child.getActive()))
                .toList();
    }
}
