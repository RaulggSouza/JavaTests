package domain;

import exceptions.ProductNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductTest {

    @Mock ProductRepository productRepository;
    @InjectMocks
    UpdateProduct sut;


    @ParameterizedTest
    @CsvSource({
            "1,prod,10.0,5", //R1
            "1,p,10.0,5", //R7
            "1,prod,10.0,0", //R8
            "1,prod,0.1,5", //R12
    })
    @DisplayName("Should return updated product and check repository usages")
    void shouldReturnUpdatedProduct(int id, String name, double doublePrice, int quantity){
        BigDecimal price = BigDecimal.valueOf(doublePrice);
        Product updatedProduct = new Product(id, name, price, quantity);
        when(productRepository.findById(id)).thenReturn(Optional.of(new Product(id, "abc", BigDecimal.valueOf(2.2), 1)));
        assertThat(sut.updateProduct(id, name, price, quantity)).isEqualTo(updatedProduct);
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).update(updatedProduct);
    }

    @ParameterizedTest
    @CsvSource({"1000"})
    @DisplayName("Should throw product not found")
    void shouldThrowProductNotFound(int id){
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ProductNotFoundException.class).
                isThrownBy(() -> sut.updateProduct(id, "abc", BigDecimal.valueOf(1.0), 1));
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, never()).update(any(Product.class));
    }
    @Nested
    @DisplayName("Should Throw null pointer")
    class TestingNullPointers{
        @AfterEach
        void verifyRepositoryIsNotCalled(){
            verify(productRepository, never()).findById(anyInt());
            verify(productRepository, never()).update(any(Product.class));
        }
        //R4
        @Test
        @DisplayName("Should throw NullPointerException when name is null")
        void shouldThrowNullPointerWhenNameIsNull(){
            assertThatNullPointerException().
                    isThrownBy(() -> sut.updateProduct(10, null, BigDecimal.valueOf(10.0), 5));
        }

        //R10
        @Test
        @DisplayName("Should throw NullPointerException when price is null")
        void shouldThrowNullPointerWhenPriceIsNull(){
            assertThatNullPointerException().
                    isThrownBy(() -> sut.updateProduct(10, "prod", null, 5));
        }
    }

    @Nested
    @DisplayName("Should throw IllegalArgumentException")
    class TestingIllegalArgument {
        @AfterEach
        void verifyRepositoryIsNotCalled(){
            verify(productRepository, never()).findById(anyInt());
            verify(productRepository, never()).update(any(Product.class));
        }
        //R3
        @Test
        @DisplayName("Should Throw IllegalArgumentException When not positive id")
        void shouldThrowIllegalArgumentExceptionWhenNotPositiveId() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> sut.updateProduct(0, "prod", BigDecimal.valueOf(10.0), 5));
        }

        //R5
        @Test
        @DisplayName("Should Throw IllegalArgumentException When empty name")
        void shouldThrowIllegalArgumentExceptionWhenEmptyName() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> sut.updateProduct(1, "", BigDecimal.valueOf(10.0), 5));
        }

        //R6
        @Test
        @DisplayName("Should Throw IllegalArgumentException When blank name")
        void shouldThrowIllegalArgumentExceptionWhenBlankName() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> sut.updateProduct(1, " ", BigDecimal.valueOf(10.0), 5));
        }

        //R9
        @Test
        @DisplayName("Should Throw IllegalArgumentException When negative quantity")
        void shouldThrowIllegalArgumentExceptionWhenNegativeQuantity() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> sut.updateProduct(1, "prod", BigDecimal.valueOf(10.0), -1));
        }

        //R11
        @Test
        @DisplayName("Should Throw IllegalArgumentException When not positive price")
        void shouldThrowIllegalArgumentExceptionWhenNotPositivePrice() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> sut.updateProduct(1, "prod", BigDecimal.valueOf(0.0), 5));
        }
    }
}