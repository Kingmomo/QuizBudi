package com.darmajayaquizz.quizbudi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.darmajayaquizz.quizbudi.Model.Quiz;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {


    Button btn1, btn2, btn3, btn4;
    TextView questionTxt, timerTxt;
    int total = 0;
    int correct = 0;
    int wrong = 0;
    private List<Quiz> quizList = new ArrayList<>();
    private FirebaseFirestore db;
    private static final String TAG = "DocSnippets";
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        pDialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        btn1 = findViewById(R.id.btnOption1);
        btn2 = findViewById(R.id.btnOption2);
        btn3 = findViewById(R.id.btnOption3);
        btn4 = findViewById(R.id.btnOption4);
        questionTxt = findViewById(R.id.questionTxt);
        timerTxt = findViewById(R.id.timerTxt);

        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        // pDialog.setIndeterminate(false);
        pDialog.show();

        getDataQuiz();
        reverseTimer(30, timerTxt);
    }

    private void getDataQuiz() {
        db.collection("Mtk")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Quiz quiz = new Quiz();
                                quiz.setAnswer(document.getString("answer"));
                                quiz.setQuestion(document.getString("question"));
                                quiz.setBtnOption1(document.getString("btnOption1"));
                                quiz.setBtnOption2(document.getString("btnOption2"));
                                quiz.setBtnOption3(document.getString("btnOption3"));
                                quiz.setBtnOption4(document.getString("btnOption4"));
                                quizList.add(quiz);
                                Collections.shuffle(quizList);

                            }
                            pDialog.dismiss();
                            mulai();
                        } else {
                            pDialog.hide();

                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private void mulai() {
        if (total > quizList.size()-1) {
            /// Open Result Activity
            Intent i = new Intent(QuizActivity.this, ResultActivity.class);
            i.putExtra("total", String.valueOf(total));
            i.putExtra("correct", String.valueOf(correct));
            i.putExtra("incorrect", String.valueOf(wrong));
            startActivity(i);
            finish();

        } else {

            System.out.println("asdas "+quizList.get(total).getQuestion());

            questionTxt.setText(quizList.get(total).getQuestion());
            btn1.setText(quizList.get(total).getBtnOption1());
            btn2.setText(quizList.get(total).getBtnOption2());
            btn3.setText(quizList.get(total).getBtnOption3());
            btn4.setText(quizList.get(total).getBtnOption4());


            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn1.getText().toString().equals(quizList.get(total).getAnswer())) {
                        btn1.setBackgroundColor(Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                correct++;
                                btn1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                mulai();
                            }
                        }, 1500);
                    } else {
                        /// answer is wrong .. we will find the correct answer, and make it green
                        wrong++;
                        btn1.setBackgroundColor(Color.RED);
                        if (btn2.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn2.setBackgroundColor(Color.GREEN);
                        } else if (btn3.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn3.setBackgroundColor(Color.GREEN);
                        } else if (btn4.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn4.setBackgroundColor(Color.GREEN);
                        }

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                mulai();
                            }
                        }, 1500);
                    }
                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn2.getText().toString().equals(quizList.get(total).getAnswer())) {
                        btn2.setBackgroundColor(Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                correct++;
                                btn2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                mulai();

                            }
                        }, 1500);
                    } else {
                        /// Answer is wrong.. We will find the correct is answer, and make it green

                        wrong++;
                        btn2.setBackgroundColor(Color.RED);

                        if (btn1.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn1.setBackgroundColor(Color.GREEN);
                        } else if (btn3.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn3.setBackgroundColor(Color.GREEN);
                        } else if (btn4.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn4.setBackgroundColor(Color.GREEN);
                        }

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                mulai();
                            }
                        }, 1500);

                    }

                }
            });

            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn3.getText().toString().equals(quizList.get(total).getAnswer())) {
                        btn3.setBackgroundColor(Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                total++;
                                correct++;
                                btn3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                mulai();

                            }
                        }, 1500);
                    } else {
                        /// Answer is wrong.. We will find the correct is answer, and make it green

                        wrong++;
                        btn3.setBackgroundColor(Color.RED);

                        if (btn1.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn1.setBackgroundColor(Color.GREEN);
                        } else if (btn2.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn2.setBackgroundColor(Color.GREEN);
                        } else if (btn4.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn4.setBackgroundColor(Color.GREEN);
                        }

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                mulai();
                            }
                        }, 1500);

                    }

                }
            });

            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn4.getText().toString().equals(quizList.get(total).getAnswer())) {
                        btn4.setBackgroundColor(Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                total++;
                                correct++;
                                btn4.setBackgroundColor(Color.parseColor("#03A9F4"));

                                mulai();

                            }
                        }, 1500);
                    } else {
                        /// Answer is wrong.. We will find the correct is answer, and make it green

                        wrong++;
                        btn4.setBackgroundColor(Color.RED);

                        if (btn1.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn1.setBackgroundColor(Color.GREEN);
                        } else if (btn2.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn2.setBackgroundColor(Color.GREEN);
                        } else if (btn3.getText().toString().equals(quizList.get(total).getAnswer())) {
                            btn3.setBackgroundColor(Color.GREEN);
                        }

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                mulai();
                                total++;
                            }
                        }, 1500);

                    }
                }
            });
        }
    }

    public void reverseTimer(int seconds, final TextView tv) {
        new CountDownTimer(seconds * 1000 + 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }


            @Override
            public void onFinish() {
                tv.setText("Completed");
                Intent myIntent = new Intent(QuizActivity.this, MainActivity.class);
                myIntent.putExtra("total", String.valueOf(total));
                myIntent.putExtra("correct", String.valueOf(correct));
                myIntent.putExtra("incorrect", String.valueOf(wrong));
                startActivity(myIntent);
            }
        }.start();
    }
}
