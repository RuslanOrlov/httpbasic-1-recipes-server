package recipes.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import recipes.dtos.IngredientWrapper;
import recipes.dtos.RecipeDTO;
import recipes.dtos.RecipeWrapper;
import recipes.models.Ingredient;
import recipes.models.Recipe;
import recipes.repositories.IngredientRepository;
import recipes.repositories.RecipeRepository;

@Service
@RequiredArgsConstructor
public class IngredientService {
	
	private final RecipeRepository recipeRepository;
	private final IngredientRepository ingredientRepository;
	
	public RecipeWrapper getAllIngredientsOfOneRecipe(Long id) {
		Recipe recipe = recipeRepository.findById(id).orElse(null);
		
		if (recipe != null) { return recipe.getRecipeWrapper(); }
		
		return null;
	}

	public IngredientWrapper getOneIngredientOfOneRecipe(Long recipeId, Long ingredientId) {
		Recipe recipe = recipeRepository
						.findById(recipeId)
						.orElse(null);
		
		if (recipe == null) { return null; }
		
		Ingredient ingredient = ingredientRepository
						.getByIdAndRecipe(ingredientId, recipe)
						.orElse(null);
		if (ingredient != null) {
			return ingredient.getIngredientWrapper();
		}
		return null;
	}

	public IngredientWrapper postIngredient(IngredientWrapper ingredientWrapper) {
		Recipe recipe = recipeRepository
						.findById(ingredientWrapper.getRecipe().getId())
						.orElse(null);
		
		if (recipe == null) { return null; }
		
		Ingredient ingredient = Ingredient.builder()
				.name(ingredientWrapper.getIngredient().getName())
				.recipe(recipe)
				.build();
		
		ingredient = ingredientRepository.save(ingredient);
		
		return ingredient.getIngredientWrapper();
	}

	public IngredientWrapper putIngredient(IngredientWrapper ingredientWrapper) {
		Recipe recipe = recipeRepository
						.findById(ingredientWrapper.getRecipe().getId())
						.orElse(null);
		
		if (recipe == null) { return null; }
		
		Ingredient updated = ingredientRepository
						.getByIdAndRecipe(ingredientWrapper.getIngredient().getId(), recipe)
						.orElse(null);
		
		if (updated != null) {
			updated.setName(
				ingredientWrapper.getIngredient().getName() != null && 
				ingredientWrapper.getIngredient().getName().trim().length() > 0 ? 
						ingredientWrapper.getIngredient().getName() : updated.getName()
				);
			return ingredientRepository.save(updated).getIngredientWrapper();
		}
		
		return null;
	}

	public void deleteIngredientById(Long id) {
		ingredientRepository.deleteById(id);
	}
	
	public void deleteIngredientsOfRecipe(RecipeDTO recipeDTO) {
		Recipe recipe = recipeRepository.findById(recipeDTO.getId()).orElse(null);
		ingredientRepository.deleteAll(ingredientRepository.getAllByRecipe(recipe));
	}	
}
