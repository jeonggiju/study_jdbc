package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

@Slf4j
public class UncheckedAppTest {

    @Test
    void unchecked(){
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(
                ()->controller.request()
        ).isInstanceOf(Exception.class);
    }


    @Test
    void printEx(){
        Controller controller = new Controller();

        try{
            controller.request();
        }catch(Exception e){
//            e.printStackTrace();
            log.info("ex", e);
        }
    }

    static class Controller{
        Service service = new Service();

        public void request(){
            service.logic();
        }
    }

    static class Service{
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic(){
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient{
        public void call(){
            throw new RuntimeConnectException("RuntimeConnectException 에러가 발생했어용");
        }
    }
    
    static class Repository{
        public void call(){
            try{
                runSQL();
            }catch(SQLException e){
                throw new RuntimeSQLException(e);
            }
        }

        private void runSQL() throws SQLException{
            throw new SQLException("SQLException 에러가 발생했어용");
        }
    }

    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException() {}

        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }

    }
}
