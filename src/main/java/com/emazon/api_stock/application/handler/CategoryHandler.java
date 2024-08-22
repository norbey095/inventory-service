package com.emazon.api_stock.application.handler;

import com.emazon.api_stock.application.dto.CategoryDto;
import com.emazon.api_stock.application.mapper.CategoryMapper;
import com.emazon.api_stock.domain.api.ICategoryServicePort;
import com.emazon.api_stock.domain.model.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final CategoryMapper categoryMapper;


    @Override
    public void saveCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.categoryDtoToCategory(categoryDto);
        categoryServicePort.saveCategory(category);
    }
}
