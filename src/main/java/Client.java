import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket(ServerConfig.HOST, ServerConfig.PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String cityFromServer = reader.readLine();
            System.out.println("Последний введенный город был: " + cityFromServer);

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Введите город:");  //даем пользователю ввод города
                var cityFromClient = scanner.nextLine();

                if (cityFromClient.matches("^[a-zA-Zа-яА-Я]*$")) { //проверяем нет ли там цифр
                    writer.println(cityFromClient); //передаем серверу наш ответ
                } else {
                    System.out.println("Неверный ввод, вы проиграли");
                }
            }

            System.out.println(reader.readLine()); //получаем ответ сервера о нашем вводе

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
