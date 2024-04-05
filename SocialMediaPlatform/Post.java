import java.util.ArrayList;
import java.util.List;

public class Post {
    private int postId;
    private String content;
    private User author;
    private List<Comment> comments;
    private List<String> likes;

    public Post(int postId, String content, User author) {
        this.postId = postId;
        this.content = content;
        this.author = author;
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
    }

    public int getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<String> getLikes() {
        return likes;
    }

    public void like(String username) {
        likes.add(username);
    }
}
