package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.TagDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.Band;
import bandfinder.models.Tag;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddBandTagServlet", value = "/addBandTag")
public class AddBandTagServlet extends ServletBase{

    @AutoInjectable
    private BandDAO bandDAO;

    @AutoInjectable
    private TagDAO tagDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tagName = req.getParameter("tagName");
        int bandId = Integer.parseInt(req.getParameter("bandId"));
        Tag tag = tagDAO.create(new Tag(tagName));
        tagDAO.addTagToBand(tag.getId(),bandId);

        resp.sendRedirect("/editBandTags.jsp?bandId=" + bandId);
    }
}
