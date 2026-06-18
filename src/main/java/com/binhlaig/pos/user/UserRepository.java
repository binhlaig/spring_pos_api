
package com.binhlaig.pos.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findFirstByShopId(Long shopId);

    Optional<User> findFirstByShopCodeIgnoreCase(String shopCode);
}
