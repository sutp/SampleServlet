package com.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Enumeration;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.bean.ConfigBean;


public class Test extends HttpServlet {
	private static ConfigBean cf = new ConfigBean();
	/**
	 * Constructor of the object.
	 */
	public Test() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		String arg  = request.getParameter("arg");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("<b>This is a sample java web pro V1.0.0</b>");
		if(code!=null){
			if(code.equals("1")){
				if(arg!=null){
					cf.setHost(getInternetIp());
					cf.setPort("8080");
					cf.setData(arg);
					cf.setUpdatetime(new Date());
					out.print("<i>Set Data Ok!</i><br>");
					out.print("<i>updateTime:"+cf.getUpdatetime()+"</i>");
				}
				else
				{
					out.print("<br><b>Error:arg is null</b>");
				}
			}
			else if(code.equals("2")){
				out.print("<br><br>");
				out.print("<i>HostAddr:</i><b>"+cf.getHost()+"</b>");
				out.print("<br><i>HostPort:</i><b>"+cf.getPort()+"</b>");
				out.print("<br><i>Data:</i><b>"+cf.getData()+"</b>");
				out.print("<br><i>UpdateTime:</i><b>"+cf.getUpdatetime()+"</b>");
			}
			else
			{
				out.print("<br><b>Error:Bad code.</b>");
			}
		}
		else{
			out.print("<br><b>Error:Bad code.</b>");
		}
		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
    private static String getIntranetIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
	
	private static String getInternetIp(){
        try{
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            Enumeration<InetAddress> addrs;
            while (networks.hasMoreElements())
            {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements())
                {
                    ip = addrs.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && ip.isSiteLocalAddress()
                            && !ip.getHostAddress().equals(getIntranetIp()))
                    {
                        return ip.getHostAddress();
                    }
                }
            }

            return getIntranetIp();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
