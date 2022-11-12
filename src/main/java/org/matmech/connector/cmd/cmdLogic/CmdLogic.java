package org.matmech.connector.cmd.cmdLogic;

import org.json.JSONObject;
import org.matmech.dataSaver.DataSaver;
import org.matmech.requestHandler.RequestHandler;
import java.util.Scanner;


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

    public void responseForCDM() {
        while(true) {
            String messageFromCMD = input.nextLine();
            StringBuilder answer = new StringBuilder("");
            DataSaver data = new DataSaver(firstName, lastName, tag, chatId);

            answer.append(requestHandler.processCmd(messageFromCMD, data));

            System.out.println(answer);
        }
    }
}
