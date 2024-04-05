import java.util.*;

public class Main {
    private static int postIdCounter = 1;
    private static int commentIdCounter = 1;
    private static SocialGraph socialGraph;
    private static Scanner scanner;

    public static void main(String[] args) {
        socialGraph = new SocialGraph();
        scanner = new Scanner(System.in);

        // Register users
        socialGraph.addUser("_shantanu_", "Shantanu", 20, "shantanu@example.com");
        socialGraph.addUser("_rushi_", "Rushikesh", 25, "rushi@example.com");
        socialGraph.addUser("charlie", "Charlie Brown", 28, "charlie@example.com");

        while (true) {
            System.out.println("\nWelcome to the Social Network!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    loginUser();
                    break;
                case 2:
                    registerUser();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();
        User loginUser = socialGraph.getUser(loginUsername);
        if (loginUser != null) {
            System.out.println("Login successful!");
            while (true) {
                System.out.println("\n1. View Profile");
                System.out.println("2. Add Friend");
                System.out.println("3. View Recommendations");
                System.out.println("4. Make a Post");
                System.out.println("5. Like a Post");
                System.out.println("6. Comment on a Post");
                System.out.println("7. Logout");
                System.out.print("Choose an option: ");
                int userOption = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (userOption) {
                    case 1:
                        socialGraph.displayUserProfile(loginUsername);
                        break;
                    case 2:
                        addFriend(loginUsername);
                        break;
                    case 3:
                        viewRecommendations(loginUsername);
                        break;
                    case 4:
                        makePost(loginUser);
                        break;
                    case 5:
                        likePost(loginUser);
                        break;
                    case 6:
                        commentOnPost(loginUser);
                        break;
                    case 7:
                        System.out.println("Logged out.");
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } else {
            System.out.println("User not found.");
        }
    }

    private static void registerUser() {
        System.out.println("Register new user:");
        System.out.print("Enter username: ");
        String regUsername = scanner.nextLine();
        System.out.print("Enter full name: ");
        String regFullName = scanner.nextLine();
        System.out.print("Enter age: ");
        int regAge = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter email: ");
        String regEmail = scanner.nextLine();
        socialGraph.addUser(regUsername, regFullName, regAge, regEmail);
        System.out.println("Registration successful!");
    }

    private static void addFriend(String username) {
        System.out.print("Enter username of friend to add: ");
        String friendUsername = scanner.nextLine();
        socialGraph.addFriendship(username, friendUsername);
    }

    private static void viewRecommendations(String username) {
        System.out.println("Recommendations for " + username + ":");
        socialGraph.recommendFriends(username).forEach(user -> System.out.println(user.getFullName()));
    }

    private static void makePost(User user) {
        System.out.print("Enter your post: ");
        String postContent = scanner.nextLine();
        Post newPost = new Post(postIdCounter++, postContent, user);
        user.addPost(newPost);
    }

    private static void likePost(User user) {
        System.out.print("Enter the username of the post author: ");
        String postAuthorUsername = scanner.nextLine();
        System.out.print("Enter the content of the post to like: ");
        String postContentToLike = scanner.nextLine();
        Post postToLike = findPostByContent(postAuthorUsername, postContentToLike);
        if (postToLike != null) {
            postToLike.like(user.getUsername());
            System.out.println("Post liked!");
        } else {
            System.out.println("Post not found.");
        }
    }

    private static void commentOnPost(User user) {
        System.out.print("Enter the username of the post author: ");
        String postAuthorUsername = scanner.nextLine();
        System.out.print("Enter the content of the post to comment: ");
        String postContentToComment = scanner.nextLine();
        Post postToComment = findPostByContent(postAuthorUsername, postContentToComment);
        if (postToComment != null) {
            System.out.print("Enter your comment: ");
            String commentContent = scanner.nextLine();
            Comment newComment = new Comment(commentIdCounter++, commentContent, user);
            postToComment.addComment(newComment);
            System.out.println("Comment added!");
        } else {
            System.out.println("Post not found.");
        }
    }

    private static Post findPostByContent(String authorUsername, String content) {
        User author = socialGraph.getUser(authorUsername);
        if (author != null) {
            List<Post> posts = author.getPosts();
            for (Post post : posts) {
                if (post.getAuthor().getUsername().equals(authorUsername) && post.getContent().equals(content)) {
                    return post;
                }
            }
        }
        return null;
    }
}
