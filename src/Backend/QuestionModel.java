package Backend;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

public class QuestionModel {
    private int id;
    private String question;
    private int userId;
    private List<CommentModel> comments = new ArrayList<>();
    private String userName;

    public QuestionModel() {}

    public QuestionModel(int id, String question, int userId, String userName) {
        this.id = id;
        this.question = question;
        this.userId = userId;
        this.userName = userName;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public List<CommentModel> getComments() {
        return comments;
    }
    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }
    public void addComment(CommentModel comment) {
        this.comments.add(comment);
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
