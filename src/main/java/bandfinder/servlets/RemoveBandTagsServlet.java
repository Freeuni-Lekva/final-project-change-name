package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.TagDAO;
import bandfinder.infrastructure.AutoInjectable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "RemoveBandTagsServlet", value = "/removeBandTags")
public class RemoveBandTagsServlet extends ServletBase{

    @AutoInjectable
    private TagDAO tagDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bandId = Integer.parseInt(req.getParameter("bandId"));
        Enumeration<String> tagNames = req.getParameterNames();
        while(tagNames.hasMoreElements()) {
            String tagName = tagNames.nextElement();
            int tagId = Integer.parseInt(req.getParameter(tagName));
            tagDAO.removeTagFromBand(tagId,bandId);
        }
        resp.sendRedirect("/editBandTags.jsp?bandId=" + bandId);
    }
}
