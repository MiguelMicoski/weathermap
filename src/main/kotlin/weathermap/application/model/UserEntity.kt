package weathermap.application.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import weathermap.application.controller.response.UserResponse

@Entity
@Table(name = "user")
class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val name: String = "",

    val email: String = "",

    val login: String = "",

    @Column(name = "password")
    val credential: String = "",

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "user_role_id")
    val role: List<RoleEntity> = mutableListOf()
) : UserDetails {

    fun toResponse(): UserResponse {
        return UserResponse(
            id = this.id!!,
            name = this.name,
            username = this.login,
            email = this.email,
        )
    }

    override fun getAuthorities(): Collection<GrantedAuthority?>? = role


    override fun getPassword(): String? = credential


    override fun getUsername(): String? = username


}