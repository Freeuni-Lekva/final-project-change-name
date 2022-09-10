package bandfinder.dao;

import bandfinder.models.Comment;

import java.util.List;

public interface CommentDAO extends DAO<Comment> {

    /**
     * @param postId specifies which posts comments we should fetch.
     * @param userId specifies the user whose comments are a priority.
     * @param limit maximum number of comments.
     * @param offset offset from the first row in the DB.
     * @return comments sorted by the number of likes.
     */
    List<Comment> getCommentBatchPriorityLikes(int postId, int userId, int limit, int offset);

    /**
     * @param postId specifies which posts comments we should fetch.
     * @param userId specifies the user whose comments are a priority.
     * @param limit maximum number of comments.
     * @param offset offset from the first row in the DB.
     * @return comments sorted by newest first.
     */
    List<Comment> getCommentBatchPriorityDate(int postId, int userId, int limit, int offset);
}
