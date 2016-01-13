package com.bright.appstarter.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findOneByEmailAddress(String email);

	long countByEmailAddress(String emailAddress);
}
