package com.example.demo.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean isActive;
    @ElementCollection(fetch=FetchType.EAGER)
    @Enumerated(jakarta.persistence.EnumType.STRING)
    @CollectionTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id")
)
@Column(name = "role")//this is added for us to know which column to look for roles in user_roles table
//else it can work without it too but for our convinebce we add it
    private List <Roles> roles;
    
@OneToMany(mappedBy="user")
@JsonManagedReference("user-tasks")
  List<Task> tasks;
    
    
   
 public List<Task> getTasks() { 
    return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public User() {
    }
    public List<Roles> getRoles() {
        return roles;
    }
    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }
 public User(String name, String email, String password) {
        this.email = email;
       // this.id = id;
        this.name = name;
        this.password = password;
        this.isActive = true;
    }

    public boolean isActive() {
       // isActive = true;
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         if (roles == null) {
        System.out.println("⚠️ Roles is NULL for user: " + email);
        return List.of();
    }
       System.out.println("User Roles for " + email + ": " + roles);
        return roles.stream()
                .map(role->new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
        
    }
    

    @Override
    public String getUsername() {
      //  throw new UnsupportedOperationException("Not supported yet.");
     return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
       return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return isActive;
    }
    
}
