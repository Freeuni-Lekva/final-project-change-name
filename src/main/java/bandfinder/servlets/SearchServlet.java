package bandfinder.servlets;

import bandfinder.dao.BandDAO;
import bandfinder.dao.UserDAO;
import bandfinder.infrastructure.AutoInjectable;
import bandfinder.models.Band;
import bandfinder.models.User;
import bandfinder.services.SearchService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchServlet", value = "/search")
public class SearchServlet extends ServletBase {
    @AutoInjectable
    private SearchService searchService;

    @AutoInjectable
    private BandDAO bandDAO;

    @AutoInjectable
    private UserDAO userDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        if(query != null) {
            if (!query.isBlank()) {
                query = query.trim();
                List<User> userResults = searchService.searchUsers(query);
                List<Band> bandResults = searchService.searchBands(query);
                request.setAttribute("user_results", userResults);
                request.setAttribute("band_results", bandResults);
            }
        }
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
