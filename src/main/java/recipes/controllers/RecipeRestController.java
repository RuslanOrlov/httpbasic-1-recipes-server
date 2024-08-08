package recipes.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
import recipes.dtos.RecipeDTO;
import recipes.dtos.RecipeWrapper;
import recipes.services.RecipeService;

//@Slf4j
@RestController
@RequestMapping(path = "/api/v1/recipes", produces = "application/json")
@RequiredArgsConstructor
public class RecipeRestController {
	
	private final RecipeService recipeService;
	
	@GetMapping
	public ResponseEntity<List<RecipeWrapper>> getAllRecipes() {
		return ResponseEntity.ok(recipeService.getAllRecipes());
	}
	
	@GetMapping(params = "sort")
	public ResponseEntity<List<RecipeWrapper>> getAllRecipes(@RequestParam String sort) {
		return ResponseEntity.ok(recipeService.getAllRecipes(sort));
	}
	
	@GetMapping(params = {"sort", "page", "size"})
	public ResponseEntity<List<RecipeWrapper>> getAllRecipes(
						@RequestParam/*("sort")*/ String sort, 
						@RequestParam/*("page")*/ int page, 
						@RequestParam/*("size")*/ int size) {
		return ResponseEntity.ok(recipeService.getAllRecipes(sort, page, size));
	}
	
	@GetMapping(params = "value")
	public ResponseEntity<List<RecipeWrapper>> getAllRecipesWithQuery(@RequestParam String value) {
		return ResponseEntity.ok(recipeService.getAllRecipesWithQuery(value));
	}
	
	@GetMapping(params = {"value", "offset", "limit"})
	public ResponseEntity<List<RecipeWrapper>> getAllRecipesWithQuery(
						@RequestParam String value, 
						@RequestParam int offset, 
						@RequestParam int limit) {
		return ResponseEntity.ok(recipeService.getAllRecipesWithQuery(value, offset, limit));
	}
	
	@GetMapping("/count")
	public Long countAll() {
		return recipeService.countAll();
	}
	
	@GetMapping(path = "/count", params = "value")
	public Long countAll(@RequestParam String value) {
		return recipeService.countAll(value);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RecipeWrapper> getRecipe(@PathVariable Long id) {
		RecipeWrapper recipeRequest = recipeService.getRecipe(id);
		if (recipeRequest == null) {
			return new ResponseEntity<RecipeWrapper>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(recipeRequest);
	}
	
	// Поддержка изображений
	@GetMapping("/{id}/image")
	public ResponseEntity<byte[]> getRecipeImage(@PathVariable Long id) {
	    byte[] image = recipeService.getRecipeImage(id);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    return new ResponseEntity<>(image, headers, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<RecipeWrapper> postRecipe(@RequestBody RecipeWrapper recipeWrapper) {
		return ResponseEntity.ok(recipeService.postRecipe(recipeWrapper));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RecipeWrapper> putRecipe(
			@PathVariable Long id, 
			@RequestBody RecipeDTO patch) {
		if (!recipeService.existsRecipeById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(recipeService.putRecipe(id, patch));
	}
		
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteRecipe(@PathVariable Long id) {
		recipeService.deleteRecipe(id);
	}
}
