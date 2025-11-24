package weathermap.application.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import weathermap.application.model.UserEntity

interface UserRepository : JpaRepository<UserEntity, Long>,
    JpaSpecificationExecutor<UserEntity> {

        fun findByLogin(login: String): UserEntity?
}