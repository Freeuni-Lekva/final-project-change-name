package bandfinder.models;

import java.util.ArrayList;
import java.util.List;

import bandfinder.dao.UserDAO;
import bandfinder.dao.BandDAO;
import bandfinder.infrastructure.Injector;

public record PostWrapper(int id, Integer authorUserId, String authorUserName,
                          Integer authorBandId, String authorBandName,
                          String text, String date) {

    public static List<PostWrapper> wrapPosts(List<Post> posts) {
        UserDAO userDAO = Injector.getImplementation(UserDAO.class);
        BandDAO bandDAO = Injector.getImplementation(BandDAO.class);
        List<PostWrapper> wrappedPosts = new ArrayList<>();
        for(Post post : posts) {
            int id = post.getId();
            System.out.println(post.getAuthorBand());
            Integer authorUserId = post.getAuthorUser();
            String authorUserName = userDAO.getById(authorUserId).getFullName();
            Integer authorBandId = post.getAuthorBand();
            String text = post.getText();
            String date = post.getDate().toString();
            PostWrapper postWrapper;
            if(authorBandId == null) {
                postWrapper = new PostWrapper(id, authorUserId, authorUserName,
                        null, null, text, date);
            }else {
                String authorBandName = bandDAO.getById(authorBandId).getName();
                postWrapper = new PostWrapper(id, authorUserId, authorUserName,
                        authorBandId, authorBandName, text, date);
            }
            wrappedPosts.add(postWrapper);
        }
        return wrappedPosts;
    }
}

