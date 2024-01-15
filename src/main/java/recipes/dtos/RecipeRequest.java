package recipes.dtos;

import java.util.List;

import lombok.Data;

@Data
public class RecipeRequest {
	
	private RecipeDTO recipe;
	private List<IngredientDTO> ingredients;
	
}
