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

    private void initUser() {
        System.out.println("Добро пожаловать! Авторизуйте свой компьютер!");
        System.out.print("Введите свое имя:");
        this.firstName = input.nextLine();
        System.out.print("Введите свою фамилию:");
        this.lastName = input.nextLine();
        System.out.print("Введите/придумайте себе тег:");
        this.tag = input.nextLine();

        System.out.println("Вы были успешно авторизированы! Для того, чтобы начать работать с ботом, напишите /start");
    }

    public CmdLogic(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        this.input = new Scanner(System.in);

        initUser();
    }

    public void responseForCDM() {
        while(true) {
            String messageFromCMD = input.nextLine();
            StringBuilder answer = new StringBuilder("");
            DataSaver data = new DataSaver(firstName, lastName, tag);

            answer.append(requestHandler.onUse(messageFromCMD, data));

            System.out.println(answer);
        }
    }
}
