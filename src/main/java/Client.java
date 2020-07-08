import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final static String IP = "localhost";
    private final static int PORT = 8080;

    @SneakyThrows
    public void start() {
        Socket socket = new Socket(IP, PORT);

        if (socket.isConnected()) {
            MessageReceiver messageReceiver = new MessageReceiver(System.in);
            MessageSender messageSender = new MessageSender(socket.getOutputStream());
            registration(messageReceiver, messageSender);

            new Thread(new SocketRunnable(socket)).start();

            String messageFromConsole;
            while ((messageFromConsole = messageReceiver.readMessage()) != null) {
                messageSender.sendMessage(messageFromConsole);
            }
        }
    }

    public void registration(MessageReceiver messageReceiver, MessageSender messageSender) {
        System.out.println("=============================");
        System.out.println("Добро пожаловать в чат!");
        System.out.println("=============================");

        System.out.println("Введите имя:");
        String name = messageReceiver.readMessage();
        System.out.println("Введите пароль:");
        String password = messageReceiver.readMessage();

        messageSender.sendMessage("Registration: " + name + " " + password);
//        messageSender.sendMessage(password);

    }
}
