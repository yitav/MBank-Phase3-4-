import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.LogDelegator;

import actionsIfc.ClientActionIfc;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private LogDelegator logDelegatorLogin = null;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        logDelegatorLogin = new LogDelegator();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("username : "+username+" password : "+password);
		
		LoginManager loginManager = LoginManager.getInstance();
		ClientActionIfc clientAction = loginManager.tryLogin(username, password);
		if(clientAction == null){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		session.setAttribute("clientAction", clientAction);
		
		logDelegatorLogin.sendToQueueWrapper
				(clientAction.viewClientDetails(username, password).getClient_id(),
						"client:"+username+" | Action:login");
		
		String mainPage = "http://localhost:8080/WebView/MainPage.jsp";
		response.sendRedirect(response.encodeRedirectURL(mainPage));
		
	}

}
