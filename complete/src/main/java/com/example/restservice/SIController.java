package com.example.restservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SIController {
	@GetMapping(value="/units/si", produces=MediaType.APPLICATION_JSON_VALUE)
	public SIUnitsConversion siUnits(@RequestParam String units) {
		return new SIUnitsConversion(units);
	}
}
