package com.mynewname.controller;

import com.mynewname.dto.NameDto;
import com.mynewname.service.NameService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NameController {

	private final NameService nameService;

	@GetMapping("/names")
	public Page<NameDto> getAllNames(Pageable pageable) {
		return nameService.findAllNames(pageable);
	}
}
