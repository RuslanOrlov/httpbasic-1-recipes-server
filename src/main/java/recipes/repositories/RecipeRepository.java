package recipes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import recipes.models.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	
	/*
	 * Аннотация @Transactional гарантирует, что весь метод (или блок кода) 
	 * выполняется в рамках одной транзакции. Данная аннотация нужна в случае 
	 * когда метод работает с большими объектами, такими как BLOB/CLOB. 
	 * Без данной аннотации выдается ошибка:
	 *   "... org.hibernate.type.descriptor.java.PrimitiveByteArrayJavaType.wrap
	 *   (PrimitiveByteArrayJavaType.java:133)\r\n\t... 141 more\r\n",
	 *   "message":"Unable to access lob stream","path":"/api/v1/recipes"}"
	 * 
	 * */
	
	@Transactional(readOnly = true)
	@Query(value = "select * from recipes "
					+ "where cast(id as varchar) like :value "
							+ "or name like :value "
							+ "or description like :value "
					+ "order by id",
			nativeQuery = true)
	List<Recipe> findRecipesWithQuery(String value);
	
	@Transactional(readOnly = true)
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
