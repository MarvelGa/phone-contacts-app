package com.contacts.phone.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.contacts.phone.entity.Permission.*;


@Slf4j
@RequiredArgsConstructor
public enum RoleEnum {
    USER(
            Set.of(
                    USER_DELETE,
                    USER_CREATE,
                    USER_FAV,
                    USER_PAY,
                    USER_REMOVE,
                    USER_DELIVERY,
                    USER_READ,
                    USER_UPDATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
