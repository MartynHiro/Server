import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(ServerConfig.PORT)) {

            String savedCity = null; //для хранения последнего города
            char lastSymbol; //последний символ в городе
            char firstSymbol;

            System.out.println("Сервер запущен");
            while (true) { //постоянная работа сервера

                try (Socket clientSocket = serverSocket.accept(); // ждем подключения клиента
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    writeCityForClient(savedCity, writer);

                    if (savedCity == null) {
                        savedCity = reader.readLine();//если это первый ввод, то записываем его
                        writer.println("OK");
                    } else {
                        lastSymbol = savedCity.toLowerCase().charAt(savedCity.length() - 1);

                        String inputCity = reader.readLine();
                        //приводим к нижнему регистру и смотрим первый символ
                        firstSymbol = inputCity.toLowerCase().charAt(0);

                        savedCity = answerForClient(savedCity, lastSymbol, firstSymbol, writer, inputCity);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String answerForClient(String savedCity, char lastSymbol, char firstSymbol, PrintWriter writer, String inputCity) {
        if (lastSymbol == firstSymbol) { //смотрим, совпадают ли буквы
            savedCity = inputCity;
            writer.println("OK"); //даем ответ клиенту
        } else {
            writer.println("NOT OK");
        }
        return savedCity;
    }

    private static void writeCityForClient(String savedCity, PrintWriter writer) {
        if (savedCity == null) {
            writer.println("???"); //??? выведется, если это первый ввод
        } else {
            writer.println(savedCity);//передаем клиенту город
        }
    }
}
