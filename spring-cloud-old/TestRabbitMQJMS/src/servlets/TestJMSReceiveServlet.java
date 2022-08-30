package servlets;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestJMSServlet
 */
@WebServlet("/TestJMSReceiver")
public class TestJMSReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestJMSReceiveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Context initCtx = new InitialContext();
			Context ctx = (Context)initCtx.lookup( "java:comp/env" );
			ConnectionFactory cf = (ConnectionFactory)ctx.lookup( "jms/RabbitMQCF" );
			Destination queue = (Destination)ctx.lookup( "jms/RabbitMQQueue" );
			Connection conn = cf.createConnection();
			System.out.println ( "Connection is created." );
			conn.start();
			System.out.println ( "Connection is started." );
			Session session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			System.out.println ( "Session is created." );
			MessageConsumer consumer = session.createConsumer(queue);
			System.out.println ( "Consumer is created." );
			Message message = consumer.receive(1000);
			if ( message != null ) {
				System.out.println ( "A message is received." );
				System.out.println( "message = " + ((TextMessage)message).getText() );
			} else {
				System.out.println( "No message is retrieved.");
			}
			session.close();
			System.out.println ( "Session is closed." );
			conn.close();
			System.out.println ( "Connection is closed." );
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		response.getWriter().append("Message sent. Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
