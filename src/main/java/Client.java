import lombok.SneakyThrows;
import utils.MyResourceBundle;
import utils.Props;

import java.net.Socket;
import java.util.Locale;

public class Client {
    private final static String IP = "localhost";
    private final static int PORT = 8080;
    private final static MyResourceBundle RESOURCE_BUNDLE = new MyResourceBundle(
            new Locale(Props.getValue("language"), Props.getValue("country")));

    @SneakyThrows
    public void start() {
        Socket socket = new Socket(IP, PORT);

        if (socket.isConnected()) {
            MessageReceiver messageReceiver = new MessageReceiver(System.in);
            MessageSender messageSender = new MessageSender(socket.getOutputStream());
            registrationOrAuthorization(messageReceiver, messageSender);

            new Thread(new SocketRunnable(socket)).start();

            String messageFromConsole;
            while ((messageFromConsole = messageReceiver.readMessage()) != null) {
                messageSender.sendMessage(messageFromConsole);
            }
        }
    }

    public void registrationOrAuthorization(MessageReceiver messageReceiver, MessageSender messageSender) {
        System.out.println("=============================");
//        System.out.println("Добро пожаловать в чат!");
        System.out.println(RESOURCE_BUNDLE.getValue("welcome"));
        System.out.println("=============================");

//        System.out.println("Нажмите:\n 1 для Авторизации: \n 2 для Регистрации: ");
        System.out.println(RESOURCE_BUNDLE.getValue("authorizationOrRegistration"));
        int numFromConsole = Integer.parseInt(messageReceiver.readMessage().trim());
        if (numFromConsole == 1) {
            String name = getString(messageReceiver, RESOURCE_BUNDLE.getValue("entryName")).toUpperCase();
            String password = getString(messageReceiver, RESOURCE_BUNDLE.getValue("entryPassword"));
            messageSender.sendMessage("Authorization " + name + " " + password);
        } else if (numFromConsole == 2) {
            String name = getString(messageReceiver, RESOURCE_BUNDLE.getValue("entryName")).toUpperCase();
            String password = getString(messageReceiver, RESOURCE_BUNDLE.getValue("entryPassword"));
            messageSender.sendMessage("Registration " + name + " " + password);
        } else {
//            System.err.println("Введите 1 или 2");
            System.err.println(RESOURCE_BUNDLE.getValue("press1or2"));
            registrationOrAuthorization(messageReceiver, messageSender);
        }

    }

    private String getString(MessageReceiver messageReceiver, String userHint) {
        System.out.println(userHint);
        return messageReceiver.readMessage().trim();
    }
}
