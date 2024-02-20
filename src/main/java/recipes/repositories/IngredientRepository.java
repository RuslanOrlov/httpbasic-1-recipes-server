package recipes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import recipes.models.Ingredient;
import recipes.models.Recipe;


public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
	
	List<Ingredient> getAllByRecipe(Recipe recipe, Sort sort);
	
	List<Ingredient> getAllByRecipe(Recipe recipe, Pageable pageable);
	
	Optional<Ingredient> getByIdAndRecipe(Long id, Recipe recipe);
	
	@Query(value = "select * from ingredients "
					+ "where cast(id as varchar) like :value and recipe_id = :recipeId "
							+ "or name like :value and recipe_id = :recipeId "
					+ "order by id",
			nativeQuery = true)
	List<Ingredient> findIngredientsWithQuery(Long recipeId, String value);
	
	@Query(value = "select * from ingredients "
					+ "where cast(id as varchar) like :value and recipe_id = :recipeId "
							+ "or name like :value and recipe_id = :recipeId "
					+ "order by id offset :offset limit :limit",
			nativeQuery = true)
	List<Ingredient> findIngredientsWithPagingQuery(
			Long recipeId, String value, Integer offset, Integer limit);
	
	@Query(value = "select count(*) from ingredients "
					+ "where cast(id as varchar) like :value and recipe_id = :recipeId "
							+ "or name like :value and recipe_id = :recipeId",
			nativeQuery = true)
	Long countAllWithQuery(Long recipeId, String value);
	
	Long countAllByRecipe(Recipe recipe);
	
}
