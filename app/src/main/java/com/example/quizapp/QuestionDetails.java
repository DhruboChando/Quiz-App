package com.example.quizapp;

public class QuestionDetails {
    private int id;
    public String question;
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    public String correct_answer;

    public QuestionDetails(String question, String option1, String option2,
                           String option3, String option4, String correct_answer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correct_answer = correct_answer;
    }

}
