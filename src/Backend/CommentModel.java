package Backend;

public class CommentModel {
    private int id;
    private String comment;
    private int questionId;
    private int userId;
    private String userName;

    public CommentModel() {}

    public CommentModel(int id, String comment, int questionId, int userId, String userName) {
        this.id = id;
        this.comment = comment;
        this.questionId = questionId;
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
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
