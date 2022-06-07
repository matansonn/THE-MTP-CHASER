package com.matan.themtpchaser;

public class Question extends QuesSuper{
    //הכרזת משתנים לClass שיצרנו
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    int correctAns;

    public Question(String question, String optionA, String optionB, String optionC, String optionD, int correctAns) {
        //הכנסת הנתונים לתוך המשתנים
        super(question);
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAns = correctAns;

    }

    public Question() {
        //בנאי ריק
        super();

    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public int getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(int correctAns) {
        this.correctAns = correctAns;
    }

    @Override
    public String toString() {
        return super.toString() +
                "question='" + question + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", correctAns=" + correctAns +
                '}';
    }
}
