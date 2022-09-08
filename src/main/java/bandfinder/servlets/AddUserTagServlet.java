package bandfinder.servlets;

import bandfinder.dao.UserDAO;
import bandfinder.dao.TagDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.Tag;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddUserTagServlet", value = "/addUserTag")
public class AddUserTagServlet extends ServletBase{

    @AutoInjectable
    private UserDAO userDAO;

    @AutoInjectable
    private TagDAO tagDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tagName = req.getParameter("tagName");
        int userId = Integer.parseInt(req.getParameter("userId"));
        Tag tag = tagDAO.create(new Tag(tagName));
        tagDAO.addTagToUser(tag.getId(),userId);

        resp.sendRedirect("/editUserTags.jsp?userId=" + userId);
    }
}
