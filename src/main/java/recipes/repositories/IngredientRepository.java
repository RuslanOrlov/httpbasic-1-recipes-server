package recipes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import recipes.models.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

}
