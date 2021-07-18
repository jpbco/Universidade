package org.therestaurant.tweb;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RemoveClients", urlPatterns = {"removeClients"}, loadOnStartup = 1) 
public class RemoveClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
        request.setAttribute("waitingListItems", TheRestaurant.waitingList.toString());
        request.setAttribute("diningRoomListItems", TheRestaurant.diningRoomList.toString());
        request.setAttribute("terraceListItems", TheRestaurant.terraceList.toString());
       // response.sendRedirect("manageClientWaitingListResponse.jsp");
        request.getRequestDispatcher("manageClientWaitingListResponse.jsp").forward(request, response);



	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            String botao = request.getParameter("botao");
            String name = request.getParameter("client_name");
            if(botao.equals("Remove from waiting list")){
                TheRestaurant.waitingList.remove(name);
            }else if(botao.equals("Remove from dining room list")){
                TheRestaurant.diningRoomList.remove(name);                  
            }else if(botao.equals("Remove from terrace list")){
                TheRestaurant.terraceList.remove(name);
            }

            request.setAttribute("waitingListItems", TheRestaurant.waitingList.toString());
            request.setAttribute("diningRoomListItems", TheRestaurant.diningRoomList.toString());
            request.setAttribute("terraceListItems", TheRestaurant.terraceList.toString());
            request.getRequestDispatcher("manageClientWaitingListResponse.jsp").forward(request, response);
}
}
