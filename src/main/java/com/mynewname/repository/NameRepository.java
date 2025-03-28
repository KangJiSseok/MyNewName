package com.mynewname.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mynewname.entity.Name;

public interface NameRepository extends JpaRepository<Name,Long> {
	Optional<Name> findByName(String name);

	long count();
}
