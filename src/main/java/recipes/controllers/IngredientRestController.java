package recipes.controllers;

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
//import lombok.extern.slf4j.Slf4j;
import recipes.dtos.IngredientWrapper;
import recipes.dtos.RecipeDTO;
import recipes.dtos.RecipeWrapper;
import recipes.services.IngredientService;

//@Slf4j
@RestController
@RequestMapping(path = "/api/v1/ingredients", produces = "application/json")
@RequiredArgsConstructor
public class IngredientRestController {
	
	private final IngredientService ingredientService;
	
	@GetMapping(params = "recipeId")
	public ResponseEntity<RecipeWrapper> getAllIngredientsOfOneRecipe(
			@RequestParam Long recipeId) {
		RecipeWrapper wrapper = ingredientService.getAllIngredientsOfOneRecipe(recipeId);
		
		if (wrapper != null) { return ResponseEntity.ok(wrapper); }
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@GetMapping(params = {"recipeId", "ingredientId"})
	public ResponseEntity<IngredientWrapper> getOneIngredientOfOneRecipe(
						@RequestParam Long recipeId,
						@RequestParam Long ingredientId) {
		IngredientWrapper wrapper = ingredientService
				.getOneIngredientOfOneRecipe(recipeId, ingredientId);
		
		if (wrapper != null) { return ResponseEntity.ok(wrapper); }
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@PostMapping
	public ResponseEntity<IngredientWrapper> postIngredient(
			@RequestBody IngredientWrapper ingredientWrapper) {
		IngredientWrapper wrapper = ingredientService.postIngredient(ingredientWrapper);
		
		if (wrapper != null) { return ResponseEntity.ok(wrapper); }
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@PutMapping
	public ResponseEntity<IngredientWrapper> putIngredient(
			@RequestBody IngredientWrapper ingredientWrapper) {
		IngredientWrapper wrapper = ingredientService.putIngredient(ingredientWrapper);
		
		if (wrapper != null) { return ResponseEntity.ok(wrapper); }
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteIngredientById(@PathVariable Long id) {
		ingredientService.deleteIngredientById(id);
	}
	
	@DeleteMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteIngredientsOfRecipe(@RequestBody RecipeDTO recipeDTO) {
		ingredientService.deleteIngredientsOfRecipe(recipeDTO);
	}
}
