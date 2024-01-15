package recipes.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.dtos.IngredientDTO;
import recipes.dtos.RecipeDTO;
import recipes.dtos.RecipeRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String description;
	
	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	private List<Ingredient> ingredients = new ArrayList<>();
	
	public RecipeDTO getRecipeDTO() {
		return RecipeDTO.builder()
				.id(id)
				.name(name)
				.description(description)
				.build();
	}
	
	public RecipeRequest getRecipeRequest() {
		RecipeDTO dto = getRecipeDTO();
		
		List<IngredientDTO> ingredientsDtos = new ArrayList<>();
		for (Ingredient ingredient : ingredients) {
			IngredientDTO ingredientDTO = IngredientDTO.builder()
					.id(ingredient.getId())
					.name(ingredient.getName())
					.build();
			ingredientsDtos.add(ingredientDTO);
		}
		
		RecipeRequest recipeRequest = new RecipeRequest();
		recipeRequest.setRecipe(dto);
		recipeRequest.setIngredients(ingredientsDtos);
		
		return recipeRequest;
	}
}
