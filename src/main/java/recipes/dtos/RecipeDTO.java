package recipes.dtos;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
	
	private Long id;
	private String name;
	private String description;
	@Lob
	private byte[] image;		/* Поддержка изображений */
	private String imageUrl;	/* Поддержка изображений */
	
}
