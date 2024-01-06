package recipes.services;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import recipes.models.Recipe;
import recipes.repositories.RecipeRepository;

@Service
@RequiredArgsConstructor
public class RecipeService {
	
	private final RecipeRepository recipeRepository;

	public List<Recipe> getAllRecipes() {
		return recipeRepository.findAll();
	}

	public List<Recipe> getAllRecipes(String sort) {
		return recipeRepository.findAll(Sort.by(sort));
	}

	public List<Recipe> getAllRecipes(String sort, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return recipeRepository.findAll(pageable).getContent();
	}

	public List<Recipe> getAllRecipesWithQuery(String value) {
		return recipeRepository.findRecipesWithQuery(value);
	}

	public List<Recipe> getAllRecipesWithQuery(String value, int offset, int limit) {
		return recipeRepository.findRecipesWithPagingQuery(value, offset, limit);
	}

	public Long countAll() {
		return recipeRepository.count();
	}

	public Long countAll(String value) {
		return recipeRepository.countAllWithQuery(value);
	}
	
	public Recipe getRecipe(Long id) {
		return recipeRepository.findById(id).orElse(null);
	}
	
	public Boolean existsRecipeById(Long id) {
		return recipeRepository.existsById(id);
	}
	
	public Recipe postRecipe(Recipe recipe) {
		return recipeRepository.save(recipe);
	}
	
	public Recipe putRecipe(Long id, Recipe patch) {
		Recipe updated = recipeRepository.findById(id).orElse(null);
		if (updated != null) {
			updated.setName(
					patch.getName() != null ? patch.getName() : updated.getName()
					);
			updated.setDescription(
					patch.getDescription() != null ? patch.getDescription() : updated.getDescription()
					);
			recipeRepository.save(updated);
		}
		return updated;
	}

	public void deleteRecipe(Long id) {
		recipeRepository.deleteById(id);		
	}

	public void deleteRecipe(Recipe recipe) {
		recipeRepository.delete(recipe);		
	}
}
