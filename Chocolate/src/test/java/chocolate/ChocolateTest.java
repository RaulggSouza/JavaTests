package chocolate;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

@Tag("UnitTests")
public class ChocolateTest {
    static Chocolate sut;
    @BeforeAll
    static void setup(){
        sut = new Chocolate();
    }

    @Nested @DisplayName("In Method Calculate Total of Chocolates")
    class CalculateTotalOfChocolates {
        @Nested @DisplayName("Valid Classes")
        class ValidClasses {
            @ParameterizedTest(name = "With ${0}, and the chocolate costing ${1} and the promotion {2}, the result should be {3}")
            @CsvSource({
                    "2,1,2,3",
                    "4,1,2,7",
                    "2,1,3,2",
                    "3,1,2,5",
                    "3,1,3,4",
                    "2,2,2,1",
                    "2,3,2,0"
            })
            @DisplayName("Should Return Total of Chocolates Earned With the Promotion")
            void shouldReturnTotalOfChocolatesEarnedWithThePromotion(double n, double c, int m, int r){
                final int result = sut.calculateTotalOfChocolates(n,c,m);
                assertThat(result).isEqualTo(r);
            }
        }

        @Nested @DisplayName("Invalid Classes")
        class InvalidClasses {
            @ParameterizedTest
            @CsvSource({
                    "-1,2,3",
                    "1,0,3",
                    "1,2,1"
            })
            @DisplayName("Should throw IllegalArgumentException")
            void shouldThrowIllegalArgumentException(double n, double c, int m) {
                assertThatIllegalArgumentException().isThrownBy(() -> sut.calculateTotalOfChocolates(n, c, m));
            }
        }
    }
}
