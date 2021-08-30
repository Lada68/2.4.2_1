package org.example.test1.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@Component
@Table(name = "roles")
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Transient

    @ManyToMany(mappedBy = "roles")
    Set<User> users;

    public Role() {
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {

        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
