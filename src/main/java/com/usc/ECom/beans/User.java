package com.usc.ECom.beans;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
// need the order in user table?  @ManyToOne 
@Entity //jpa //the object  mapping to DB// obj/relational mapping ////put in IOC container
@Table(name = "usc_user") //default use class name "User" if nothing here
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;////serialVersionUID field provides a version identifier for the class, which is used during the deserialization process to verify that the sender and receiver of a serialized object have loaded classes for that object that are compatible with respect to serialization.

    @Id  // primary key
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")// enum: GenerationType
    @SequenceGenerator(name = "SEQ", sequenceName = "USC_USER_SEQ",allocationSize = 1)
    private int id;
    @NotNull
    @Size(min=2, message = "username should be more than 2 letters.")
    @Column(name = "username", unique = true, nullable = false) 
    private String username;
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "c_user_c_user_profile", // "c_user_c_user_profile" not "usc_user_profile"
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "user_profile_id", referencedColumnName = "id") }
    )
    private List<UserProfile> profiles = new ArrayList<UserProfile>();
    //?// @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserDetail userDetail;

/* in user table in db
 * "authorities": [  //?????
            {
                "id": 1,
                "type": "ROLE_ADMIN",
                "authority": "ROLE_ADMIN"
            }
        ],
	   "enabled": true,
	    "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true
 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//?
        return profiles;
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

    ///////////////////////////////////////////????????????above
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public List<UserProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<UserProfile> profiles) {
        this.profiles = profiles;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public User(int id, String username, String password, List<UserProfile> profiles) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.profiles = profiles;
    }
	public User() {
		super();
	}
	 @Override 
	    public String toString() {
	        return "User [id=" + id + ", username=" + username + ", password=" + password + ", profiles=" + profiles + "]";
	    }


    
    
    
    
}
