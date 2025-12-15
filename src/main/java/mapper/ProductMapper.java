package mapper;

import com.local_cart.dto.request.ProductRequest;
import com.local_cart.dto.response.ProductResponse;
import com.local_cart.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BrandMapper.class, CategoryMapper.class, CountryMapper.class})
public interface ProductMapper {

    Product toEntity(ProductRequest productRequest);

    ProductResponse toResponse(Product product);

    void updateProduct(ProductRequest productRequest, @MappingTarget Product product);
}
