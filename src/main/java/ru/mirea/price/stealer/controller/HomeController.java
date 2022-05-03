package ru.mirea.price.stealer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mirea.price.stealer.dto.api.cheapshark.stores.StoreDto;
import ru.mirea.price.stealer.service.api.StoresApiService;

import java.util.Comparator;

@Controller
public class HomeController {
	@Autowired
	private StoresApiService storesApiService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/home/cheapest-price")
	public String searchCheapestPrice() {
		return "cheapest-price";
	}

	@GetMapping("/home/store")
	public String ShowGameStores() {
		return "game-stores";
	}

	@GetMapping("/home/store-search")
	public String searchInStore(Model model) {
		model.addAttribute("stores", storesApiService.storesInfo(Comparator.comparing(StoreDto::getStoreName)));
		return "store-search";
	}
}
