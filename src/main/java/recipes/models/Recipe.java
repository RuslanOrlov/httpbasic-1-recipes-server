package recipes.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.dtos.IngredientDTO;
import recipes.dtos.RecipeDTO;
import recipes.dtos.RecipeWrapper;

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
	
	@Lob
	private byte[] image; /* Поддержка изображений */
	
	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	private List<Ingredient> ingredients = new ArrayList<>();
	
	public RecipeDTO getRecipeDTO() {
		return RecipeDTO.builder()
				.id(id)
				.name(name)
				.description(description)
				.image(null) 									/* Поддержка изображений */
				.imageUrl(image != null && image.length > 0 ?	/* Поддержка изображений */
					"http://localhost:8080/api/v1/recipes/" + id + "/image" : 
					null)
				.build();
	}
	
	public RecipeWrapper getRecipeWrapper() {
		RecipeDTO dto = getRecipeDTO();
		
		List<IngredientDTO> ingredientsDtos = new ArrayList<>();
		for (Ingredient ingredient : ingredients) {
			IngredientDTO ingredientDTO = IngredientDTO.builder()
					.id(ingredient.getId())
					.name(ingredient.getName())
					.build();
			ingredientsDtos.add(ingredientDTO);
		}
		
		RecipeWrapper recipeWrapper = new RecipeWrapper();
		recipeWrapper.setRecipe(dto);
		recipeWrapper.setIngredients(ingredientsDtos);
		
		return recipeWrapper;
	}
}
