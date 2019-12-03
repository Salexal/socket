package wenjing.socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wenjing.socket.Netty.NettyServer;

@SpringBootApplication
public class SocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketApplication.class, args);
        try {
            new NettyServer(55).start();
            System.out.println(1);
        }catch(Exception e) {
            System.out.println("NettyServerError:"+e.getMessage());
        }
    }

}
