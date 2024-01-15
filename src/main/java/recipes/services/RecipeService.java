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
import recipes.dtos.RecipeRequest;
import recipes.models.Ingredient;
import recipes.models.Recipe;
import recipes.repositories.RecipeRepository;

@Service
@RequiredArgsConstructor
public class RecipeService {
	
	private final RecipeRepository recipeRepository;

	public List<RecipeRequest> getAllRecipes() {
		List<Recipe> recipes = recipeRepository.findAll();
		List<RecipeRequest> list = new ArrayList<>();
		
		for (Recipe recipe : recipes) {
			list.add(recipe.getRecipeRequest());
		}
		
		return list;
	}

	public List<RecipeRequest> getAllRecipes(String sort) {
		List<Recipe> recipes = recipeRepository.findAll(Sort.by(sort));
		List<RecipeRequest> list = new ArrayList<>();
		
		for (Recipe recipe : recipes) {
			list.add(recipe.getRecipeRequest());
		}
		
		return list;
	}

	public List<RecipeRequest> getAllRecipes(String sort, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
				
		List<Recipe> recipes = recipeRepository.findAll(pageable).getContent();
		List<RecipeRequest> list = new ArrayList<>();
		
		for (Recipe recipe : recipes) {
			list.add(recipe.getRecipeRequest());
		}
		
		return list;
	}

	public List<RecipeRequest> getAllRecipesWithQuery(String value) {
		List<Recipe> recipes = recipeRepository.findRecipesWithQuery(value);
		List<RecipeRequest> list = new ArrayList<>();
		
		for (Recipe recipe : recipes) {
			list.add(recipe.getRecipeRequest());
		}
		
		return list;
	}

	public List<RecipeRequest> getAllRecipesWithQuery(String value, int offset, int limit) {
		List<Recipe> recipes = recipeRepository.findRecipesWithPagingQuery(value, offset, limit);
		List<RecipeRequest> list = new ArrayList<>();
		
		for (Recipe recipe : recipes) {
			list.add(recipe.getRecipeRequest());
		}
		
		return list;
	}

	public Long countAll() {
		return recipeRepository.count();
	}

	public Long countAll(String value) {
		return recipeRepository.countAllWithQuery(value);
	}
	
	public RecipeRequest getRecipe(Long id) {
		RecipeRequest recipeRequest = null;
		
		Recipe recipe = recipeRepository.findById(id).orElse(null);
		
		if (recipe != null) {
			recipeRequest = recipe.getRecipeRequest();
		}
		return recipeRequest;
	}
	
	public Boolean existsRecipeById(Long id) {
		return recipeRepository.existsById(id);
	}
	
	public RecipeRequest postRecipe(RecipeRequest recipeRequest) {
		
		Recipe recipe = Recipe.builder()
				.name(recipeRequest.getRecipe().getName())
				.description(recipeRequest.getRecipe().getDescription())
				.build();
		
		List<Ingredient> ingredients = new ArrayList<>();
		for (IngredientDTO ingredientDTO : recipeRequest.getIngredients()) {
			Ingredient ingredient = Ingredient.builder()
					.name(ingredientDTO.getName())
					.recipe(recipe)
					.build();
			ingredients.add(ingredient);
		}
		recipe.setIngredients(ingredients);
		
		recipeRepository.save(recipe);
		
		return recipe.getRecipeRequest();
	}
	
	public RecipeRequest putRecipe(Long id, RecipeDTO patch) {
		Recipe updated = recipeRepository.findById(id).orElse(null);
		if (updated != null) {
			updated.setName(
					patch.getName() != null ? patch.getName() : updated.getName()
					);
			updated.setDescription(
					patch.getDescription() != null ? patch.getDescription() : updated.getDescription()
					);
			return recipeRepository.save(updated).getRecipeRequest();
		}
		return null;
	}
		
	public void deleteRecipe(Long id) {
		recipeRepository.deleteById(id);		
	}
}
