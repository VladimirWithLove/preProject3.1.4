package ru.kata.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "login")
    @NotEmpty(message = "Логин не должен быть пустым")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(max = 100, message = "Имя должно быть длиной до 100 символов")
    private String name;
    @Column(name = "surname")
    @NotEmpty(message = "Фамилия не должна быть пустой")
    @Size(max = 100, message = "Фамилия должна быть длиной до 100 символов")
    private String surname;
    @Column(name = "age")
    @Min(value = 0, message = "Возраст не должен быть меньше 0")
    @Max(value = 150, message = "Возраст должен быть меньше 150")
    private int age;
    @Column(name = "profession")
    private String profession;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
    }

    public User(String name, String surname, int age, String profession) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.profession=profession;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public String getRolesString() {
        for (Role role: getRoles()) {
            if (role.getId() == 2L) {
                return "ADMIN USER";
            }
        }
        return "USER";
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setAdminRole() {
        roles.add(new Role(2L,"ROLE_ADMIN"));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", profession='" + profession + '\'' +
                ", roles=" + roles +
                '}';
    }
}
