package org.matmech.connector.cmdLogic;

import org.json.JSONObject;
import org.matmech.dataSaver.DataSaver;
import org.matmech.requestHandler.RequestHandler;
import java.util.Scanner;


public class CmdLogic {
    private Scanner input;
    private RequestHandler requestHandler;
    private String firstName;
    private String lastName;
    private String tag;
    private long id;

    public CmdLogic(String firstName, String lastName, String tag, long id, RequestHandler requestHandler) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tag = tag;
        this.id = id;
        this.requestHandler = requestHandler;
        this.input = new Scanner(System.in);

        System.out.println("Hello, i'm bot");
    }

    public void responseForCDM() {
        while(true) {
            String messageFromCMD = input.nextLine();
            StringBuilder answer = new StringBuilder("");
            DataSaver data = new DataSaver(firstName, lastName, tag, id);

            answer.append(requestHandler.onUse(messageFromCMD, data));

            System.out.println(answer);
        }
    }
}
