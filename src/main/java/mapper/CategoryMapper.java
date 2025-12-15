package mapper;

import com.local_cart.dto.request.CategoryRequest;
import com.local_cart.dto.response.CategoryChildrenResponse;
import com.local_cart.dto.response.CategoryResponse;
import com.local_cart.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    Category toEntity(CategoryRequest CategoryRequest);

    CategoryResponse toResponse(Category Category);

    void updateCategory(CategoryRequest CategoryRequest, @MappingTarget Category Category);

    CategoryChildrenResponse toResponseWithChildren(Category category);

}
