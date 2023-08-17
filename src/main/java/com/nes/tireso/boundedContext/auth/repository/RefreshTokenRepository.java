package com.nes.tireso.boundedContext.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nes.tireso.boundedContext.auth.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	void deleteByUsername(String userEmail);

	Optional<RefreshToken> findByUsername(String username);

}
