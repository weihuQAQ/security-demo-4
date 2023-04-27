package com.hw.securitydemo4.entry;

import com.hw.securitydemo4.enums.PermissionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private PermissionType permission;

    @Column(unique = true)
    private String permissionName;

    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = {@JoinColumn(referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(referencedColumnName = "id")}
    )
    private Set<Role> roles = new HashSet<>();
}
