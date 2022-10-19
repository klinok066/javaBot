package org.matmech.connector.cmdLogic;

import org.json.JSONObject;
import org.matmech.requestHandler.RequestHandler;
import java.util.Scanner;


public class CmdLogic {
    private Scanner input;
    private RequestHandler requestHandler;
    private String firstName;
    private String lastName;

    public CmdLogic(String firstName, String lastName, RequestHandler requestHandler) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.requestHandler = requestHandler;
        this.input = new Scanner(System.in);

        System.out.println("Hello, i'm bot");
    }

    public void responseForCDM() {
        while(true) {
            String messageFromCMD = input.nextLine();
            StringBuilder answer = new StringBuilder("");
            JSONObject info = requestHandler.getInfo(firstName, lastName);

            answer.append(requestHandler.onUse(messageFromCMD, info));

            System.out.println(answer);
        }
    }
}
