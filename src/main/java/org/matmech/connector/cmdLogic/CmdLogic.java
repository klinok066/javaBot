package org.matmech.connector.cmdLogic;

import org.json.JSONObject;
import org.matmech.dataSaver.DataSaver;
import org.matmech.requestHandler.RequestHandler;
import java.util.Scanner;

/**
 * Логика класса-интерфейса, который реализует консольную обертку над ботом
 */
public class CmdLogic {
    private Scanner input;
    private RequestHandler requestHandler;
    private String firstName;
    private String lastName;
    private String tag;

    public CmdLogic(String firstName, String lastName, String tag, RequestHandler requestHandler) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tag = tag;
        this.requestHandler = requestHandler;
        this.input = new Scanner(System.in);
    }

    /**
     * Метод, получает сообщения с консоли и обрабатывает их
     */
    public void startChat() {
        while(true) {
            String messageFromCMD = input.nextLine();
            StringBuilder answer = new StringBuilder("");
            DataSaver data = new DataSaver(firstName, lastName, tag);

            answer.append(requestHandler.onUse(messageFromCMD, data));

            System.out.println(answer);
        }
    }
}
