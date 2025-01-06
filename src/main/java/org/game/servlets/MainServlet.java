package org.game.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.game.model.RequestData;
import org.game.model.ResponseData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/game")
public class MainServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/plain");
        response.getWriter().write("Hey this is the text from servlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //Присвоение типа контента
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //1) Чтение тела Json из реквеста
        StringBuilder builder =  new StringBuilder();
        try(BufferedReader reader = request.getReader()){
            String line;
            while ((line = reader.readLine())!=null){
                builder.append(line);
            }
        }
        String requestBody = builder.toString();
        System.out.printf("Полученный Json: %s", requestBody);

        //2) Преобразование Джейсона в джава обЪект
        RequestData requestData = gson.fromJson(requestBody, RequestData.class);

        //3) Реализация логики сервера
        String receiveMsg = (requestData!=null && requestData.getMessage()!=null)
                ? requestData.getMessage()
                : "No data received";

        //4) Отправка ответа клиенту
        ResponseData responseData = new ResponseData("OK", "Получено: " + receiveMsg);

        //5) Java-Objekt -> JSON
        String jsonResponse = gson.toJson(responseData);

        //6) send response to client as JSON
        try(PrintWriter writer = response.getWriter()){
            writer.print(jsonResponse);
            writer.flush();
        }




    }
}
