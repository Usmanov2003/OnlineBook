package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.online.constant.BookStoreConstants;
import com.book.online.constant.ResponseCode;
import com.book.online.constant.db.UsersDBConstants;
import com.book.online.model.User;
import com.book.online.model.UserRole;
import com.book.online.service.UserService;
import com.book.online.service.impl.UserServiceImpl;

public class CustomerRegisterServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(BookStoreConstants.CONTENT_TYPE_TEXT_HTML);

        String pWord = req.getParameter(UsersDBConstants.COLUMN_PASSWORD);
        String fName = req.getParameter(UsersDBConstants.COLUMN_FIRSTNAME);
        String lName = req.getParameter(UsersDBConstants.COLUMN_LASTNAME);
        String addr = req.getParameter(UsersDBConstants.COLUMN_ADDRESS);
        String phNo = req.getParameter(UsersDBConstants.COLUMN_PHONE);
        String mailId = req.getParameter(UsersDBConstants.COLUMN_MAILID);
        User user = new User();
        user.setEmailId(mailId);
        user.setFirstName(fName);
        user.setLastName(lName);
        user.setPassword(pWord);
        user.setPhone(Long.parseLong(phNo));
        user.setAddress(addr);
        try {
            String respCode = userService.register(UserRole.CUSTOMER, user);
            System.out.println(respCode);
            if (ResponseCode.SUCCESS.name().equalsIgnoreCase(respCode)) {
                RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
                rd.include(req, res);
                pw.println("<table class=\"tab\"><tr><td>User Registered Successfully</td></tr></table>");
            } else {
                RequestDispatcher rd = req.getRequestDispatcher("CustomerRegister.html");
                rd.include(req, res);
                pw.println("<table class=\"tab\"><tr><td>" + respCode + "</td></tr></table>");
                pw.println("Sorry for interruption! Try again");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
