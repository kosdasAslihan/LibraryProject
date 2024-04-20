package com.io.libraryproject.entity.enums;

public enum RoleType {

    ROLE_ADMIN("Administrator"),
    ROLE_STAFF("Staff"),
    ROLE_MEMBER("Member");

    private String name;
    private RoleType(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
