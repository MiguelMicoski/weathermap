package weathermap.application.repository

import org.springframework.data.jpa.repository.JpaRepository
import weathermap.application.model.RoleEntity

interface RoleRepository : JpaRepository<RoleEntity, Long> {
}