package com.enigma.enigma_shop;

import com.enigma.enigma_shop.entity.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController // untuk membuat API
public class EnigmaShopApplication1File {
	public static void main(String[] args) {
		SpringApplication.run(EnigmaShopApplication.class, args);
	}
//""""
//	{
//	  "id": "1",
//    "name": "Apel",
//    "price": 3000
//	}
//"""

	// produces = ini adalah dari server kita, kita mau mengembalikan data apa ke client, kalau disini kita kembalikan data JSON
	// consume = itudari client ke server, maksudnya server kalian mengkonsumsi data apa?
	@GetMapping( path = "/hello-world", produces = MediaType.APPLICATION_JSON_VALUE)
//	@RequestMapping(method = RequestMethod.GET, path = "/hello-world") //  cara lama
	public String helloWorld() {
		return "<h1> Hello World </h1>";
	}

	@GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getProduct(){
		return Map.of(
						"id", "1",
						"name", "Apel",
						"price", 3000
		);
	}

	// query param
	//http://localhost:8080/menus?name=...&maxPrice=....
	@GetMapping(path = "/menus")
	public List<Map<String, Object>> getMenusFilter(
					@RequestParam(name = "name", required = false) String nimi,
					@RequestParam(name = "maxPrice", required = false) Integer maxPrice
	) {
		Map<String, Object> menu = Map.of(
						"id", 1,
						"name", ""+nimi,
						"price", ""+maxPrice
		);
		List<Map<String, Object>> menus = new ArrayList<>();
		menus.add(menu);
		return menus;
	}

	// path variable
	// bisanya buat findbyid
	@GetMapping(path = "/menus/{id}")
	public String getMenuById(@PathVariable String id){
		return "Menu dengan id " + id;
	}

	@PostMapping(
					path = "/products",
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public Product createNewProduct(@RequestBody Product product){
		return product;
	}
}
