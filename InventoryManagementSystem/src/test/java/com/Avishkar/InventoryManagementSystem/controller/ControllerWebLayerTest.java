package com.Avishkar.InventoryManagementSystem.controller;

import com.Avishkar.InventoryManagementSystem.dto.*;
import com.Avishkar.InventoryManagementSystem.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {
        BrandController.class,
        CategoryController.class,
        RoleController.class,
        StyleController.class,
        VariantController.class,
        StoreController.class,
        InventoryController.class,
        InventoryEventController.class,
        UserController.class
})
@AutoConfigureMockMvc(addFilters = false)
class ControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private BrandService brandService;
    @MockBean private CategoryService categoryService;
    @MockBean private RoleService roleService;
    @MockBean private StyleService styleService;
    @MockBean private VariantService variantService;
    @MockBean private StoreService storeService;
    @MockBean private InventoryService inventoryService;
    @MockBean private InventoryEventService inventoryEventService;
    @MockBean private UserService userService;

    @Test
    void getBrandsReturnsList() throws Exception {
        BrandDto dto = new BrandDto();
        dto.setId(1L);
        dto.setName("Brand");
        dto.setDescription("Desc");
        dto.setCreatedAt(LocalDateTime.now());
        when(brandService.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/brands"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Brand"));

        verify(brandService).getAll();
        verifyNoMoreInteractions(brandService);
    }

    @Test
    void createCategoryReturnsCreated() throws Exception {
        CategoryDto dto = new CategoryDto();
        dto.setId(2L);
        dto.setName("Men");
        dto.setDescription("Menswear");
        when(categoryService.create(any(CategoryCreateRequest.class))).thenReturn(dto);

        CategoryCreateRequest request = new CategoryCreateRequest();
        request.setName("Men");
        request.setDescription("Menswear");

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Men"));

        verify(categoryService).create(any(CategoryCreateRequest.class));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void getRoleByIdReturnsDto() throws Exception {
        RoleDto dto = new RoleDto();
        dto.setId(3L);
        dto.setName("Manager");
        when(roleService.getById(3L)).thenReturn(dto);

        mockMvc.perform(get("/api/roles/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Manager"));

        verify(roleService).getById(3L);
        verifyNoMoreInteractions(roleService);
    }

    @Test
    void updateStyleReturnsUpdatedDto() throws Exception {
        StyleDto dto = new StyleDto();
        dto.setId(4L);
        dto.setName("Hoodie");
        dto.setBrandId(1L);
        dto.setCategoryId(2L);
        dto.setIsActive(true);
        dto.setIsListedOnline(true);
        when(styleService.update(any(Long.class), any(StyleUpdateRequest.class))).thenReturn(dto);

        StyleUpdateRequest request = new StyleUpdateRequest();
        request.setName("Hoodie");
        request.setSeason("Winter");
        request.setIsActive(true);
        request.setIsListedOnline(true);
        request.setBrandId(1L);
        request.setCategoryId(2L);

        mockMvc.perform(put("/api/styles/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Hoodie"));

        verify(styleService).update(any(Long.class), any(StyleUpdateRequest.class));
        verifyNoMoreInteractions(styleService);
    }

    @Test
    void deleteVariantReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/variants/10"))
                .andExpect(status().isNoContent());

        verify(variantService).delete(10L);
        verifyNoMoreInteractions(variantService);
    }

    @Test
    void createStoreReturnsCreated() throws Exception {
        StoreDto dto = new StoreDto();
        dto.setId(5L);
        dto.setName("Main Store");
        dto.setIsActive(true);
        dto.setIsPublic(true);
        when(storeService.create(any(StoreCreateRequest.class))).thenReturn(dto);

        StoreCreateRequest request = new StoreCreateRequest();
        request.setName("Main Store");
        request.setLocation("City Center");
        request.setIsActive(true);
        request.setIsPublic(true);

        mockMvc.perform(post("/api/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value("Main Store"));

        verify(storeService).create(any(StoreCreateRequest.class));
        verifyNoMoreInteractions(storeService);
    }

    @Test
    void updateInventoryReturnsDto() throws Exception {
        InventoryDto dto = new InventoryDto();
        dto.setId(6L);
        dto.setQuantity(25);
        dto.setReorderLevel(5);
        dto.setStoreId(1L);
        dto.setVariantId(2L);
        when(inventoryService.update(any(Long.class), any(InventoryUpdateRequest.class))).thenReturn(dto);

        InventoryUpdateRequest request = new InventoryUpdateRequest();
        request.setQuantity(25);
        request.setReorderLevel(5);
        request.setStoreId(1L);
        request.setVariantId(2L);

        mockMvc.perform(put("/api/inventories/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.quantity").value(25));

        verify(inventoryService).update(any(Long.class), any(InventoryUpdateRequest.class));
        verifyNoMoreInteractions(inventoryService);
    }

    @Test
    void createInventoryEventReturnsCreated() throws Exception {
        InventoryEventDto dto = new InventoryEventDto();
        dto.setId(7L);
        dto.setEventType("IN");
        dto.setQuantityChange(5);
        dto.setInventoryId(6L);
        when(inventoryEventService.create(any(InventoryEventCreateRequest.class))).thenReturn(dto);

        InventoryEventCreateRequest request = new InventoryEventCreateRequest();
        request.setEventType("IN");
        request.setQuantityChange(5);
        request.setReason("Restock");
        request.setInventoryId(6L);

        mockMvc.perform(post("/api/inventory-events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.eventType").value("IN"));

        verify(inventoryEventService).create(any(InventoryEventCreateRequest.class));
        verifyNoMoreInteractions(inventoryEventService);
    }

    @Test
    void deleteUserReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/9"))
                .andExpect(status().isNoContent());

        verify(userService).delete(9L);
        verifyNoMoreInteractions(userService);
    }
}
