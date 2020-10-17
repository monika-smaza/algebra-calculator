import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/calculator")
public class Calculator extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">");


        out.println(
                "<div class=\"container\" style=\"text-align:center;width:50%\">" +
                        "<h1>Instrukcja:</h1>" +
                        "<p1>znak % oznacza operacje modulo</p1></br>" +
                        "<p1>x, y oznaczaja zmienne</p1></br>" +
                        "<p1>przykladowe poprawnie wprowadzone wyrazenie:</p1></br>" +
                        "<p1>(x+y*5)%20</p1></br>" +
                        "<p1>(x+y)%50</p1></br>" +
                        "<p1>x*x*x+5*y</p1></br>" +
                        "<p1 style=\"color:red\">przykladowe blednie wprowadzone wyrazenie:</p1></br>" +
                        "<p1 style=\"color:red\">5x*6y</p1></br>" +
                        "<p1 style=\"color:red\">5*x*y *4</p1></br>" +
                        "<p1 style=\"color:red\">x*ymod50</p1></br>" +
                        "<form action=\"calculator\" class=\"text-center border border-light p-5\" method=\"post\">" +
                        "poczatek zakresu:<input type=\"text\" class=\"form-control mb-4\"  placeholder=\"poczatek zakresu\" name=\"first\"/>" +
                        "koniec zakresu:<input type=\"text\" class=\"form-control mb-4\"  placeholder=\"koniec zakresu\" name=\"last\"/>" +
                        "dzialanie:<input type=\"text\" class=\"form-control mb-4\"  placeholder=\"dzialanie\" name=\"operation\"/>" +
                        "<input type=\"submit\" class=\"btn btn-info btn-block\" value=\"policz\"/>" +
                        "</form></div>"
        );
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        Integer first = Integer.parseInt(req.getParameter("first"));
        Integer last = Integer.parseInt(req.getParameter("last"));
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine se = manager.getEngineByName("JavaScript");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">");
        out.println("<div class=\"container\">" +
                        "<a class=\"btn btn-primary btn-lg btn-block\" href=\"\\AlgebraCalc\\calculator\" role=\"button\">Back</a>" +
                "<div class =\"table-responsive\"><table class=\"table table-hover\">");
        for(int i = first-1 ; i <= last ; i++){
            if(i == first-1){
                out.println("<thead><tr><th></th>");
            }else {
                out.println("<tr><th>" + i + "</th>");
            }
            for(int j= first ; j <= last ; j++){
                try {
                    if(i == first-1){
                        out.println("<th>"+ j +"</th>");
                        if(j == last) out.println("</thead><tbody>");
                    }else{
                        String xChar = Integer.toString(i);
                        String yChar = Integer.toString(j);
                        String operationString = operation.replaceAll("x", xChar).replaceAll("y", yChar);
                        out.println("<td>"+ se.eval(operationString) +"</td>");
                    }
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }
            out.println("</tr>");
        }
        out.println("</tbody></table></div></div>");
    }

}
