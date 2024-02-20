package recipes.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import recipes.dtos.IngredientDTO;
import recipes.dtos.RecipeDTO;
import recipes.dtos.RecipeWrapper;
import recipes.models.Ingredient;
import recipes.models.Recipe;
import recipes.repositories.RecipeRepository;

@Service
@RequiredArgsConstructor
public class RecipeService {
	
	private final RecipeRepository recipeRepository;

	public List<RecipeWrapper> getAllRecipes() {
		List<Recipe> recipes = recipeRepository.findAll();
		List<RecipeWrapper> list = new ArrayList<>();
		
		for (Recipe recipe : recipes) {
			list.add(recipe.getRecipeWrapper());
		}
		
		return list;
	}

	public List<RecipeWrapper> getAllRecipes(String sort) {
		List<Recipe> recipes = recipeRepository.findAll(Sort.by(sort));
		List<RecipeWrapper> list = new ArrayList<>();
		
		for (Recipe recipe : recipes) {
			list.add(recipe.getRecipeWrapper());
		}
		
		return list;
	}

	public List<RecipeWrapper> getAllRecipes(String sort, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
				
		List<Recipe> recipes = recipeRepository.findAll(pageable).getContent();
		List<RecipeWrapper> list = new ArrayList<>();
		
		for (Recipe recipe : recipes) {
			list.add(recipe.getRecipeWrapper());
		}
		
		return list;
	}

	public List<RecipeWrapper> getAllRecipesWithQuery(String value) {
		List<Recipe> recipes = recipeRepository.findRecipesWithQuery(value);
		List<RecipeWrapper> list = new ArrayList<>();
		
		for (Recipe recipe : recipes) {
			list.add(recipe.getRecipeWrapper());
		}
		
		return list;
	}

	public List<RecipeWrapper> getAllRecipesWithQuery(String value, int offset, int limit) {
		List<Recipe> recipes = recipeRepository.findRecipesWithPagingQuery(value, offset, limit);
		List<RecipeWrapper> list = new ArrayList<>();
		
		for (Recipe recipe : recipes) {
			list.add(recipe.getRecipeWrapper());
		}
		
		return list;
	}
	
	public Long countAll() {
		return recipeRepository.count();
	}

	public Long countAll(String value) {
		return recipeRepository.countAllWithQuery(value);
	}
	
	public RecipeWrapper getRecipe(Long id) {
		RecipeWrapper recipeRequest = null;
		
		Recipe recipe = recipeRepository.findById(id).orElse(null);
		
		if (recipe != null) {
			recipeRequest = recipe.getRecipeWrapper();
		}
		return recipeRequest;
	}
	
	public Boolean existsRecipeById(Long id) {
		return recipeRepository.existsById(id);
	}
	
	public RecipeWrapper postRecipe(RecipeWrapper recipeWrapper) {
		
		Recipe recipe = Recipe.builder()
				.name(recipeWrapper.getRecipe().getName())
				.description(recipeWrapper.getRecipe().getDescription())
				.build();
		
		List<Ingredient> ingredients = new ArrayList<>();
		for (IngredientDTO ingredientDTO : recipeWrapper.getIngredients()) {
			Ingredient ingredient = Ingredient.builder()
					.name(ingredientDTO.getName())
					.recipe(recipe)
					.build();
			ingredients.add(ingredient);
		}
		recipe.setIngredients(ingredients);
		
		recipeRepository.save(recipe);
		
		return recipe.getRecipeWrapper();
	}
	
	public RecipeWrapper putRecipe(Long id, RecipeDTO patch) {
		Recipe updated = recipeRepository.findById(id).orElse(null);
		if (updated != null) {
			updated.setName(
					patch.getName() != null && 
					patch.getName().trim().length() > 0 ? 
							patch.getName() : updated.getName()
					);
			updated.setDescription(
					patch.getDescription() != null && 
					patch.getDescription().trim().length() > 0 ? 
							patch.getDescription() : updated.getDescription()
					);
			return recipeRepository.save(updated).getRecipeWrapper();
		}
		return null;
	}
		
	public void deleteRecipe(Long id) {
		recipeRepository.deleteById(id);		
	}
}
