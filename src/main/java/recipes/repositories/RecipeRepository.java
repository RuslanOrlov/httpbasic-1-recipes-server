package recipes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import recipes.models.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	
	@Query(value = "select * from recipes "
					+ "where cast(id as varchar) like :value "
							+ "or name like :value "
							+ "or description like :value "
					+ "order by id",
			nativeQuery = true)
	List<Recipe> findRecipesWithQuery(String value);
	
	@Query(value = "select * from recipes "
					+ "where cast(id as varchar) like :value "
							+ "or name like :value "
							+ "or description like :value "
					+ "order by id offset :offset limit :limit",
			nativeQuery = true)
	List<Recipe> findRecipesWithPagingQuery(String value, Integer offset, Integer limit);
	
	@Query(value = "select count(*) from recipes "
					+ "where cast(id as varchar) like :value "
							+ "or name like :value "
							+ "or description like :value ",
			nativeQuery = true)
	Long countAllWithQuery(String value);
	
}
