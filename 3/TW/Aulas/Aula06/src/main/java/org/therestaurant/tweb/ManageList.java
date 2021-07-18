package org.therestaurant.tweb;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ManageList", urlPatterns = {"manageList"}, loadOnStartup = 1) 
public class ManageList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		response.getWriter().append("Welcome to The Restaurant. ");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            String botao = request.getParameter("botao");
            if(TheRestaurant.waitingList.size()>0){
                if(botao.equals("Move client to the dining room")){
                    String client = TheRestaurant.waitingList.remove(0);
                    TheRestaurant.diningRoomList.add(client);
                    
                }else if(botao.equals("Move client to the terrace")){
                    String client = TheRestaurant.waitingList.remove(0);
                    TheRestaurant.terraceList.add(client);
                    
                }

            }

            request.setAttribute("waitingListSize", TheRestaurant.waitingList.size());
            request.setAttribute("waitingListItems", TheRestaurant.waitingList.toString());
            request.setAttribute("diningRoomListItems", TheRestaurant.diningRoomList.toString());
            request.setAttribute("terraceListItems", TheRestaurant.terraceList.toString());
            request.setAttribute("diningRoomListSize", TheRestaurant.diningRoomList.size());
            request.setAttribute("terraceListSize", TheRestaurant.terraceList.size());
            request.getRequestDispatcher("manageClientWaitingListResponse.jsp").forward(request, response);
}
}
