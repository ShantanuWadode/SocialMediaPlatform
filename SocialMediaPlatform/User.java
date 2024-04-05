import java.util.*;

public class User {
    private String username;
    private String fullName;
    private int age;
    private String email;
    private Set<User> friends;
    private List<Post> posts;

    public User(String username, String fullName, int age, String email) {
        this.username = username;
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.friends = new HashSet<>();
        this.posts = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public boolean isFriend(User user) {
        return friends.contains(user);
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }
}
