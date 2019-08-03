package com.darmajayaquizz.quizbudi.Model;

public class Quiz {

    public String question,btnOption1, btnOption2, btnOption3, btnOption4, answer;

    public Quiz(String question, String btnOption1, String btnOption2, String btnOption3, String btnOption4, String answer) {
        this.question = question;
        this.btnOption1 = btnOption1;
        this.btnOption2 = btnOption2;
        this.btnOption3 = btnOption3;
        this.btnOption4 = btnOption4;
        this.answer = answer;
    }

    public Quiz() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getBtnOption1() {
        return btnOption1;
    }

    public void setBtnOption1(String btnOption1) {
        this.btnOption1 = btnOption1;
    }

    public String getBtnOption2() {
        return btnOption2;
    }

    public void setBtnOption2(String btnOption2) {
        this.btnOption2 = btnOption2;
    }

    public String getBtnOption3() {
        return btnOption3;
    }

    public void setBtnOption3(String btnOption3) {
        this.btnOption3 = btnOption3;
    }

    public String getBtnOption4() {
        return btnOption4;
    }

    public void setBtnOption4(String btnOption4) {
        this.btnOption4 = btnOption4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
