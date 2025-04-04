package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch(){
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw(){
        Service service = new Service();
        assertThatThrownBy(()->service.callThrow()).isInstanceOf(MyCheckedException.class);

    }

    /**
     * Checked 예외는
     * 예외를 잡아서 처리하거나, 던지거나 둘 중 하나를 필수로 선택해야 한다.
     */
    static class Service{
         Repository repository =  new Repository();

        /**
         * 예외를 잡아서 처리하는 코드
         */
        public void callCatch(){
            try{
                repository.call();
            }catch(MyCheckedException e){
                // 예외 처리 로직
                log.info("예외 처리, message={}", e.getMessage(), e);
            }catch(Exception e){
                log.info("Exception 상위 계층으로 에러를 처리", e.getMessage(), e);
            }
        }

        /**
         * 체크 예외를 밖으로 던지는 코드
         * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야한다.
         */
        public void callThrow() throws MyCheckedException{
            repository.call();
        }
    }

    /**
     * 체크 예외를 밖으로 던지는 코드
     * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야한다.
     */
    static class Repository{
        public void call() throws MyCheckedException {
            throw new MyCheckedException("error message");
        }
    }

    static class MyCheckedException extends Exception{
        public MyCheckedException(String message){
            super(message);
        }
    }

}
