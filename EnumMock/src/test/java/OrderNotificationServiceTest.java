import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderNotificationServiceTest {

    @Mock CustomerRepository repo;
    @Mock NotificationGateway gateway;
    @InjectMocks OrderNotificationService sut;

    @ParameterizedTest
    @CsvSource({
            "CREATED,Your order has been created.", //R4
            "PAID,Your order has been paid.", //R5
            "SHIPPED,Your order has been shipped.", //R6
            "DELIVERED,Your order has been delivered.", //R7
            "CANCELLED,Your order has been cancelled." //R8
    })
    @DisplayName("Should send a message of the status")
    void shouldSendAMessageOfTheStatus(String status, String message){
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "email@teste.com");

        when(repo.findById(customerId)).thenReturn(Optional.of(customer));

        sut.notifyOrderStatus(customerId, OrderStatus.valueOf(status));

        verify(repo, times(1)).findById(customerId);
        verify(gateway, times(1)).send(anyString(), eq(message));
    }

    //R3
    @Test
    @DisplayName("Should throw IllegalArgumentException when customer not exists")
    void shouldThrowIllegalArgumentExceptionWhenCustomerNotExists(){
        UUID uuid = UUID.randomUUID();
        when(repo.findById(uuid)).thenReturn(Optional.empty());

        assertThatIllegalArgumentException().isThrownBy(() ->
                sut.notifyOrderStatus(uuid, OrderStatus.CREATED));

        verify(repo,times(1)).findById(uuid);
        verifyNoInteractions(gateway);
    }

    @Nested
    @DisplayName("Should throw NullPointerException")
    class NullPointers {
        @AfterEach
        void verification(){
            verifyNoInteractions(repo);
            verifyNoInteractions(gateway);
        }

        //R1
        @Test
        @DisplayName("When customer_id is null")
        void shouldThrowNullPointerExceptionWhenCustomerIdIsNull(){
            assertThatNullPointerException().isThrownBy(() ->
                    sut.notifyOrderStatus(null, OrderStatus.CREATED));
        }

        //R2
        @Test
        @DisplayName("When status is null")
        void shouldThrowNullPointerExceptionWhenStatusIsNull(){
            assertThatNullPointerException().isThrownBy(() ->
                    sut.notifyOrderStatus(UUID.randomUUID(), null));
        }
    }
}