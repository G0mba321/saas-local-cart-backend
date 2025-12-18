package com.local_cart.mapper;

import com.local_cart.dto.request.CategoryRequest;
import com.local_cart.dto.response.CategoryChildrenResponse;
import com.local_cart.dto.response.CategoryResponse;
import com.local_cart.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "root", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    Category toEntity(CategoryRequest CategoryRequest);

    CategoryResponse toResponse(Category Category);

    @Mapping(target = "root", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    void updateCategory(CategoryRequest CategoryRequest, @MappingTarget Category Category);

    CategoryChildrenResponse toResponseWithChildren(Category category);

}
