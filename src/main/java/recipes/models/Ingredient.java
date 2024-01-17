package recipes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.dtos.IngredientDTO;
import recipes.dtos.IngredientWrapper;
import recipes.dtos.RecipeDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingredients")
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	
	public IngredientDTO getIngredientDTO() {
		return IngredientDTO.builder()
				.id(id)
				.name(name)
				.build();
	}
	
	public IngredientWrapper getIngredientWrapper() {
		RecipeDTO recipeDTO = recipe.getRecipeDTO();
		IngredientDTO ingredientDTO = getIngredientDTO();
		
		IngredientWrapper ingredientWrapper = new IngredientWrapper();
		ingredientWrapper.setRecipe(recipeDTO);
		ingredientWrapper.setIngredient(ingredientDTO);
		
		return ingredientWrapper;
	}
	
}
