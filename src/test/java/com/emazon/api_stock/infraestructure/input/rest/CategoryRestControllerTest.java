package com.emazon.api_stock.infraestructure.input.rest;

import com.emazon.api_stock.application.dto.CategoryDto;
import com.emazon.api_stock.application.handler.ICategoryHandler;
import com.emazon.api_stock.infraestructure.util.ConstantsTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(CategoryRestController.class)
class CategoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryHandler categoryHandler;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        categoryDto = new CategoryDto();
        categoryDto.setName(ConstantsTest.FIELD_NAME.getMessage());
        categoryDto.setDescription(ConstantsTest.FIELD_DESCRIPTION.getMessage());
    }

    @Test
    void createCategory_ShouldReturnStatusCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ConstantsTest.URL_CREATE_CATEGORY.getMessage())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(categoryHandler, Mockito.times(1)).saveCategory(categoryDto);
    }

    @Test
    void getAllCategory_ShouldReturnCategoryDto() throws Exception {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(categoryDto);
        Mockito.when(categoryHandler.getAllCategorys(1, 1,false)).thenReturn(categoryDtoList);


        mockMvc.perform(MockMvcRequestBuilders.get(ConstantsTest.URL_GET_CATEGORY.getMessage())
                        .param("page", "1")
                        .param("size", "1")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                                .value(ConstantsTest.FIELD_NAME.getMessage()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].description")
                                .value(ConstantsTest.FIELD_DESCRIPTION.getMessage()));

        Mockito.verify(categoryHandler, Mockito.times(1))
                .getAllCategorys(1,1,false);
    }
}