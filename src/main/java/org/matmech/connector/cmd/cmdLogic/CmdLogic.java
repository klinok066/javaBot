package org.matmech.connector.cmd.cmdLogic;

import org.matmech.userData.UserData;
import org.matmech.requests.requestHandler.RequestHandler;
import java.util.Scanner;

/**
 * Логика класса-интерфейса, который реализует консольную обертку над ботом
 */
public class CmdLogic {
    private final Scanner input;
    private final RequestHandler requestHandler;
    private final String firstName;
    private final String lastName;
    private final String tag;
    private final int chatId;

    public CmdLogic(String firstName, String lastName, String tag, int chatId, RequestHandler requestHandler) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tag = tag;
        this.chatId = chatId;
        this.requestHandler = requestHandler;
        this.input = new Scanner(System.in);
    }

    /**
     * Метод, получает сообщения с консоли и обрабатывает их
     */
    public void responseForCDM() {
        while(true) {
            String messageFromCMD = input.nextLine();
            StringBuilder answer = new StringBuilder("");
            UserData data = new UserData(firstName, lastName, tag, chatId);

            answer.append(requestHandler.execute(messageFromCMD, data));

            System.out.println(answer);
        }
    }
}
