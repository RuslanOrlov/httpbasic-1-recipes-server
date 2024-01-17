package recipes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import recipes.models.Ingredient;
import recipes.models.Recipe;


public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
	
	List<Ingredient> getAllByRecipe(Recipe recipe);
	
	Optional<Ingredient> getByIdAndRecipe(Long id, Recipe recipe);

	@Query(value = "select * from ingredients "
					+ "where cast(id as varchar) like :value "
							+ "or name like :value "
					+ "order by id",
			nativeQuery = true)
	List<Ingredient> findIngredientsWithQuery(String value);
	
	@Query(value = "select * from ingredients "
					+ "where cast(id as varchar) like :value "
							+ "or name like :value "
					+ "order by id offset :offset limit :limit",
			nativeQuery = true)
	List<Ingredient> findIngredientsWithPagingQuery(String value, Integer offset, Integer limit);
	
	@Query(value = "select count(*) from ingredients "
					+ "where cast(id as varchar) like :value "
							+ "or name like :value ",
			nativeQuery = true)
	Long countAllWithQuery(String value);
	
}
