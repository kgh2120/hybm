package com.dragontrain.md.domain.user.infra;

import com.dragontrain.md.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
