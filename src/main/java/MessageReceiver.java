import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@AllArgsConstructor
public class MessageReceiver {
    public final BufferedReader reader;

    public MessageReceiver(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @SneakyThrows
    public String readMessage() {

//        String massage ;
//        if ((massage = readerFromUser.readLine()) != null) {
//            return massage;
//        }

        return reader.readLine();

    }
}
