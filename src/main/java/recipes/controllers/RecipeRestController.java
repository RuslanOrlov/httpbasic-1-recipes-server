package recipes.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import recipes.dtos.RecipeDTO;
import recipes.dtos.RecipeRequest;
import recipes.services.RecipeService;

@RestController
@RequestMapping(path = "/api/v1/recipes", produces = "application/json")
@RequiredArgsConstructor
public class RecipeRestController {
	
	private final RecipeService recipeService;
	
	@GetMapping
	public ResponseEntity<List<RecipeRequest>> getAllRecipes() {
		return ResponseEntity.ok(recipeService.getAllRecipes());
	}
	
	@GetMapping(params = "sort")
	public ResponseEntity<List<RecipeRequest>> getAllRecipes(@RequestParam String sort) {
		return ResponseEntity.ok(recipeService.getAllRecipes(sort));
	}
	
	@GetMapping(params = {"sort", "page", "size"})
	public ResponseEntity<List<RecipeRequest>> getAllRecipes(@RequestParam("sort") String sort, 
									@RequestParam("page") int page, 
									@RequestParam("size") int size) {
		return ResponseEntity.ok(recipeService.getAllRecipes(sort, page, size));
	}
	
	@GetMapping(params = "value")
	public ResponseEntity<List<RecipeRequest>> getAllRecipesWithQuery(@RequestParam String value) {
		return ResponseEntity.ok(recipeService.getAllRecipesWithQuery(value));
	}
	
	@GetMapping(params = {"value", "offset", "limit"})
	public ResponseEntity<List<RecipeRequest>> getAllRecipesWithQuery(@RequestParam String value, 
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
	public ResponseEntity<RecipeRequest> getRecipe(@PathVariable Long id) {
		RecipeRequest recipeRequest = recipeService.getRecipe(id);
		if (recipeRequest == null) {
			return new ResponseEntity<RecipeRequest>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(recipeRequest);
	}
	
	@PostMapping
	public ResponseEntity<RecipeRequest> postRecipe(@RequestBody RecipeRequest recipeRequest) {
		return ResponseEntity.ok(recipeService.postRecipe(recipeRequest));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RecipeRequest> putRecipe(
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
