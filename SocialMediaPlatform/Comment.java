public class Comment {
    private int commentId;
    private String content;
    private User author;

    public Comment(int commentId, String content, User author) {
        this.commentId = commentId;
        this.content = content;
        this.author = author;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }
}
