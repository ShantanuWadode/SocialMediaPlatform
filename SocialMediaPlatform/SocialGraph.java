import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class SocialGraph {
    private Map<String, User> users;

    public SocialGraph() {
        this.users = new HashMap<>();
    }

    public void addUser(String username, String fullName, int age, String email) {
        users.put(username, new User(username, fullName, age, email));
    }

    public void addFriendship(String username1, String username2) {
        User user1 = users.get(username1);
        User user2 = users.get(username2);
        if (user1 != null && user2 != null) {
            user1.addFriend(user2);
            user2.addFriend(user1);
        } else {
            System.out.println("One or both users not found.");
        }
    }

    public Set<User> recommendFriends(String username) {
        User user = users.get(username);
        Set<User> recommendations = new HashSet<>();
        if (user != null) {
            for (User friend : user.getFriends()) {
                for (User mutualFriend : friend.getFriends()) {
                    if (!user.isFriend(mutualFriend) && !mutualFriend.equals(user)) {
                        recommendations.add(mutualFriend);
                    }
                }
            }
        } else {
            System.out.println("User not found.");
        }
        return recommendations;
    }

    public Set<User> findMutualConnections(String username1, String username2) {
        User user1 = users.get(username1);
        User user2 = users.get(username2);
        Set<User> mutualConnections = new HashSet<>();
        if (user1 != null && user2 != null) {
            for (User friend : user1.getFriends()) {
                if (user2.isFriend(friend)) {
                    mutualConnections.add(friend);
                }
            }
        } else {
            System.out.println("One or both users not found.");
        }
        return mutualConnections;
    }

    public void displayUserProfile(String username) {
        User user = users.get(username);
        if (user != null) {
            System.out.println("Username: " + user.getUsername());
            System.out.println("Full Name: " + user.getFullName());
            System.out.println("Age: " + user.getAge());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Friends: ");
            for (User friend : user.getFriends()) {
                System.out.println("- " + friend.getUsername());
            }
            System.out.println("Posts: ");
            for (Post post : user.getPosts()) {
                System.out.println("Post ID: " + post.getPostId());
                System.out.println("Content: " + post.getContent());
                System.out.println("Likes: " + post.getLikes());
                System.out.println("Comments: ");
                for (Comment comment : post.getComments()) {
                    System.out.println("- " + comment.getContent());
                }
            }
        } else {
            System.out.println("User not found.");
        }
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }
}
