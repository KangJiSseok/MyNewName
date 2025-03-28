package com.mynewname.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mynewname.dto.NameDto;
import com.mynewname.repository.NameRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NameService {
	private final NameRepository nameRepository;

	public Page<NameDto> findAllNames(Pageable pageable) {
		return nameRepository.findAll(pageable)
			.map(name -> NameDto.builder()
				.name(name.getName())
				.count(name.getCount()).build()
			);
	}
}
