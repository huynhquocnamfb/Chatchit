package vn.edu.nlu.web.chat.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.enums.Gender;
import vn.edu.nlu.web.chat.enums.Role;
import vn.edu.nlu.web.chat.enums.SocialStatus;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {
    private static final String NO_LOCATION = "-";

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "app_name", nullable = false)
    private String appName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_status")
    private SocialStatus socialStatus = SocialStatus.OFFLINE;

    @Column(name = "location")
    private String location = User.NO_LOCATION; // append

    @Column(name = "is_locked")
    private boolean isLocked;

    @Column(name = "roles")
    private String roles;

    @Override
    public void prePersist() {
        super.setEntityStatus(EntityStatus.ACTIVE);
    }

    public User() {
        this.roles = Role.ROLE_USER.name();
        this.isLocked = false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split(", "))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return EntityStatus.ACTIVE == this.getEntityStatus();
    }

}