package com.example.calcolatricescientifica;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.MathContext;


public class CalcolatriceScientifica extends Application {

    private String primoTermine="";
    private String secondoTermine="";
    private String operation="";
    private TextArea textArea;
    private HBox riga0;
    private HBox riga1;
    private HBox riga2;
    private HBox riga3;
    private HBox riga4;
    private HBox riga5;
    private VBox vBox;
    private BorderPane root;
    private HBox hBoxText;
    private Stage myStage;
    private boolean activeProMode=false;
    private boolean backFromResult=false;
    private boolean logPressed=false;
    private boolean radixPressed=false;

    @Override
    public void start(Stage stage) {
        myStage=stage;
        setScene();
        root.setCenter(vBox);
        root.setTop(hBoxText);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        displayZero();
    }
    private boolean subHandler1(String pressed){
        boolean breakOrNot=false;
        if (primoTermine.equals("") && secondoTermine.equals("") && operation.equals("") ) {
            primoTermine = "1";
            operation = pressed;
        } else if (operation.equals("") && secondoTermine.equals("")) {
            operation = pressed;
        } else if (secondoTermine.equals("")) {
            if (pressed.equals("log")) {
                logPressed = true;
                displayLog();
            } else {
                radixPressed = true;
                displaySqrt();
            }
            breakOrNot=true;
        }
        return breakOrNot;
    }
    public void subHandler2(){
        if (primoTermine.equals("")) {
            secondoTermine = "";
            operation = "";
        } else if ((operation.equals("x") || operation.equals("÷")) ) {
            operation = "+";
        } else if (!operation.equals("") ) {
            secondoTermine = "+" + secondoTermine;
            reformat();
        } else {
            operation = "+";
        }
    }
    public void subHandler3(String pressed){
        if (primoTermine.equals("")) {
            secondoTermine = "";
            operation = "";
            if(pressed.equals("-")){primoTermine = "-";}
        } else if (operation.equals("x") || operation.equals("÷")) {
            operation = pressed;
        } else if (!operation.equals("")) {
            secondoTermine = pressed + secondoTermine;
            reformat();
        } else {
            operation = pressed;
        }
    }
    public void subHandler4(){
        if ((!primoTermine.equals("") && secondoTermine.equals("")) || !operation.equals("")) {
            operation = "x";
        } else {
            displayZero();
        }
    }
    public void subHandler5(){
        if ((!primoTermine.equals("") && secondoTermine.equals("")) || !operation.equals("")) {
            operation = "÷";
        } else {
            displayZero();
        }
    }

    public void subHandler6(){
        if (secondoTermine.equals("")) {
            displayResult(new BigDecimal(primoTermine));
        } else {
            BigDecimal primo = new BigDecimal(primoTermine);
            BigDecimal secondo = new BigDecimal(secondoTermine);

            //manage different operations
            switch (operation) {
                case "log":
                    primoTermine = primo.multiply(Utils.logBase10(secondo)) + "";
                    displayResult(primo.multiply(Utils.logBase10(secondo)));
                    break;
                case "√":
                    BigDecimal multiply = primo.multiply(secondo.sqrt(new MathContext(10)));
                    primoTermine = multiply + "";
                    displayResult(multiply);
                    break;
                case "pow":
                    primoTermine = Utils.pow(primo, secondo) + "";
                    displayResult(Utils.pow(primo, secondo));
                    break;
                case "+":
                    if (logPressed) {
                        primoTermine = primo.add(Utils.logBase10(secondo)) + "";
                        displayResult(primo.add(Utils.logBase10(secondo)));
                        logPressed = false;
                        break;
                    } else if (radixPressed) {
                        primoTermine = primo.add(secondo.sqrt(new MathContext(10))) + "";
                        displayResult(secondo.sqrt(new MathContext(10)));
                        radixPressed = false;
                        break;
                    }
                    primoTermine = primo.add(secondo) + "";
                    displayResult(primo.add(secondo));
                    break;
                case "-":
                    if (logPressed) {
                        primoTermine = primo.subtract(Utils.logBase10(secondo)) + "";
                        displayResult(primo.subtract(Utils.logBase10(secondo)));
                        logPressed = false;
                        break;
                    } else if (radixPressed) {
                        primoTermine = primo.subtract(secondo.sqrt(new MathContext(10))) + "";
                        displayResult(secondo.sqrt(new MathContext(10)));
                        radixPressed = false;
                        break;
                    }
                    primoTermine = primo.subtract(secondo) + "";
                    displayResult(primo.subtract(secondo));
                    break;
                case "x":
                    if (logPressed) {
                        primoTermine = primo.multiply(Utils.logBase10(secondo)) + "";
                        displayResult(primo.multiply(Utils.logBase10(secondo)));
                        logPressed = false;
                        break;
                    } else if (radixPressed) {
                        primoTermine = primo.multiply(secondo.sqrt(new MathContext(10))) + "";
                        displayResult(secondo.sqrt(new MathContext(10)));
                        radixPressed = false;
                        break;
                    }
                    primoTermine = primo.multiply(secondo) + "";
                    displayResult(primo.multiply(secondo));
                    break;
                case "÷":
                    if (logPressed) {
                        primoTermine = primo.divide(Utils.logBase10(secondo)) + "";
                        displayResult(primo.divide(Utils.logBase10(secondo)));
                        logPressed = false;
                        break;
                    } else if (radixPressed) {
                        primoTermine = primo.divide(secondo.sqrt(new MathContext(10))) + "";
                        displayResult(secondo.sqrt(new MathContext(10)));
                        radixPressed = false;
                        break;
                    }
                    if (secondo.equals(new BigDecimal("0"))) {
                        displayInfinity();
                    } else {
                        primoTermine = primo.divide(secondo) + "";
                        displayResult(primo.divide(secondo));
                    }
                    break;
                default:
            }
        }
    }
    public void subHandler7(){
        if (primoTermine.length() >= 1 && operation.equals("")) {
            if (!primoTermine.contains(".")) {
                primoTermine = primoTermine + ".";
            }
        } else if (secondoTermine.length() >= 1 && !secondoTermine.contains(".")) {
            secondoTermine = secondoTermine + ".";
        }
        display();
    }
    public void subHandler8(){
        if (secondoTermine.length() >= 1) {
            secondoTermine = secondoTermine.substring(0, secondoTermine.length() - 1);
        } else if (!operation.equals("")) {
            operation = "";
        } else if (primoTermine.length() >= 1) {
            primoTermine = primoTermine.substring(0, primoTermine.length() - 1);
        } else {
            displayZero();
        }
        display();
    }
    public void subHandler9(){
        String segno = "-";
        if (primoTermine.equals("") || operation.equals("")) {
            primoTermine = segno.concat(primoTermine);
            if (primoTermine.startsWith("--")) primoTermine = primoTermine.substring(2);
            else if (primoTermine.startsWith("+-") || primoTermine.startsWith("--")) {
                primoTermine = "-".concat(primoTermine.substring(2));
            }
        } else {
            secondoTermine = segno.concat(secondoTermine);
            reformat();
        }
        display();
    }
    public void subHandler10(String pressed){
        if(pressed.equals("π")){
            pressed= String.valueOf(Math.PI);
        }
        if (backFromResult) {
            primoTermine = pressed;
            operation = "";
            secondoTermine = "";
        }else if(logPressed){
            secondoTermine=secondoTermine.concat(pressed);
            displayLog();
        }else if(radixPressed){
            secondoTermine=secondoTermine.concat(pressed);
            displaySqrt();
        }
        else if (primoTermine.equals("") || operation.equals("")) {
            primoTermine = primoTermine.concat(pressed);
        } else {
            secondoTermine = secondoTermine.concat(pressed);
        }
        display();
    }
    public boolean subHandler0(String pressed){
        boolean breakOrNot=false;
        if (backFromResult) {
            backFromResult = false;
        }
        if (!primoTermine.equals("") && !secondoTermine.equals("") && !operation.equals("") ) {
            helper(pressed);
        }
        else if(pressed.equals("√") || pressed.equals("log")) {
            if(subHandler1(pressed)){breakOrNot=true;}
        }
        else if(pressed.equals("+")) {
            subHandler2();

        }
        else if(pressed.equals("pow") || pressed.equals("-")){
            subHandler3(pressed);
        }
        else if (pressed.equals("x")){
            subHandler4();
        }
        else { //caso .equals("÷")
            subHandler5();
        }
        display();
        return breakOrNot;
    }
    private void handler(String pressed) {

        //manage pressed buttons
        switch (pressed) {
            // case
            case "√","log","+","pow","-","x","÷" -> {
                subHandler0(pressed);
            }

            // case =
            case "=" -> {
                subHandler6();
                backFromResult = true;
                operation = "";
                secondoTermine = "";
            }

            //case C
            case "C" -> {
                displayZero();
                logPressed=false;
                radixPressed=false;
            }

            //case .
            case "." -> {
                subHandler7();
            }

            //case ⌫
            case "⌫" -> {
               subHandler8();
            }

            //case ±
            case "±" -> {
                subHandler9();
            }

            //default
            default -> {
                subHandler10(pressed);
            }
        }

    }

    private void displayLog() {
        this.textArea.setText(primoTermine+" "+operation+" "+"log "+secondoTermine);
    }

    private void displaySqrt() {
        this.textArea.setText(primoTermine+" "+operation+" "+"√ "+secondoTermine);
    }

    private void reformat() {
        if((operation+secondoTermine).startsWith("--")){
            operation="+";
        }else if((operation+secondoTermine).startsWith("+-") || primoTermine.startsWith("--")){
            operation="-";
        }else if((operation+secondoTermine).startsWith("++")){
            operation="+";
        }
        secondoTermine=secondoTermine.substring(1);
    }

    private void display(){
        if(primoTermine.equals("") && secondoTermine.equals("") && operation.equals("")){
            displayZero();
        }else {
            this.textArea.setText(primoTermine + " " + operation + " " + secondoTermine);
        }
    }

    private void displayResult(BigDecimal result){
        this.textArea.setText(result+"");
    }

    private void displayZero(){
        primoTermine="";
        operation="";
        secondoTermine="";
        this.textArea.setText("0");
    }

    private void displayInfinity(){
        primoTermine="";
        operation="";
        secondoTermine="";
        this.textArea.setText("Infinity");
    }


    private void proMode() {
        if(!activeProMode){
        vBox.getChildren().clear();
        vBox.getChildren().addAll(riga0,riga1,riga2,riga3,riga4,riga5);
        myStage.setHeight(500);
        activeProMode=true;
        }
        else{
            vBox.getChildren().clear();
            vBox.getChildren().addAll(riga1,riga2,riga3,riga4,riga5);
            myStage.setHeight(450);
            activeProMode=false;
        }

    }

    private Button setNewButton(String text){
        Button btn=new Button(text);
        btn.setMinSize(50,50);
        btn.setStyle("-fx-font-weight: bold;");
        btn.setOnAction(e -> handler(text));
        return btn;
    }

    public void helper(String who){ //se non viene schiacciato uguale ma un altra operazione
        BigDecimal primo = new BigDecimal(primoTermine);
        BigDecimal secondo = new BigDecimal(secondoTermine);
        switch (operation) {
            case "log":
                primoTermine=primo.multiply(Utils.logBase10(secondo))+"";
                displayResult(primo.multiply(Utils.logBase10(secondo)));
                operation = who;
                break;
            case "√":
                BigDecimal multiply = primo.multiply(secondo.sqrt(new MathContext(10)));
                primoTermine= multiply +"";
                displayResult(multiply);
                operation = who;
                break;
            case "+":
                displayResult(primo.add(secondo));
                primoTermine = primo.add(secondo) + "";
                operation = who;
                break;
            case "-":
                displayResult(primo.subtract(secondo));
                primoTermine = primo.subtract(secondo)+ "";
                operation = who;
                break;
            case "x":
                displayResult(primo.multiply(secondo));
                primoTermine = primo.multiply(secondo) + "";
                operation = who;
                break;
            case "÷":
                if (secondo.equals(new BigDecimal("0"))) {
                    displayInfinity();
                } else {
                    displayResult(primo.divide(secondo));
                    primoTermine = primo.divide(secondo) + "";
                    operation = who;
                }
                break;
            default:
        }
        secondoTermine = "";
    }

    private void setScene(){
        String myColor="-fx-background-color: #e0aaff";
        root=new BorderPane();
        root.setMinSize(300,400);

        vBox=new VBox();
        vBox.setStyle("-fx-background-color: #200f54");
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        riga0=new HBox();
        riga0.setSpacing(10);
        riga0.setAlignment(Pos.CENTER);

        riga1=new HBox();
        riga1.setSpacing(10);
        riga1.setAlignment(Pos.CENTER);

        riga2=new HBox();
        riga2.setSpacing(10);
        riga2.setAlignment(Pos.CENTER);

        riga3=new HBox();
        riga3.setSpacing(10);
        riga3.setAlignment(Pos.CENTER);

        riga4=new HBox();
        riga4.setSpacing(10);
        riga4.setAlignment(Pos.CENTER);

        riga5=new HBox();
        riga5.setSpacing(10);
        riga5.setAlignment(Pos.CENTER);

        Button btnLog=setNewButton("log");
        Button btnSquare=setNewButton("√");
        Button btnPi=setNewButton("π");
        Button btnPow=setNewButton("pow");
        btnLog.setStyle(myColor);
        btnSquare.setStyle(myColor);
        btnPi.setStyle(myColor);
        btnPow.setStyle(myColor);

        riga0.getChildren().addAll(btnLog,btnSquare,btnPi,btnPow);

        Button btnCE= setNewButton("±");
        Button btnC= setNewButton("C");
        Button btnDel= setNewButton("⌫");
        Button btnDiv= setNewButton("÷");

        riga1.getChildren().addAll(btnCE,btnC,btnDel,btnDiv);

        Button btn7= setNewButton("7");
        Button btn8= setNewButton("8");
        Button btn9= setNewButton("9");
        Button btnPer= setNewButton("x");

        riga2.getChildren().addAll(btn7,btn8,btn9,btnPer);

        Button btn4= setNewButton("4");
        Button btn5= setNewButton("5");
        Button btn6= setNewButton("6");
        Button btnMeno= setNewButton("-");

        riga3.getChildren().addAll(btn4,btn5,btn6,btnMeno);

        Button btn1= setNewButton("1");
        Button btn2= setNewButton("2");
        Button btn3= setNewButton("3");
        Button btnPiu= setNewButton("+");

        riga4.getChildren().addAll(btn1,btn2,btn3,btnPiu);

        Button btnPro= new Button("pro");
        btnPro.setMinSize(50,50);
        btnPro.setStyle("-fx-background-color: #c77dff");
        btnPro.setOnAction(e -> {
            proMode();
            if(activeProMode){btnPro.setText("back");}
            else{btnPro.setText("pro");}
        });

        Button btn0= setNewButton("0");
        Button btnPt= setNewButton(".");
        Button btnUguale= setNewButton("=");
        btnUguale.setStyle("-fx-background-color: #4361ee");

        riga5.getChildren().addAll(btnPro,btn0,btnPt,btnUguale);

        textArea=new TextArea();
        textArea.setMaxHeight(50);
        textArea.setMaxWidth(230);
        textArea.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        hBoxText =new HBox(textArea);
        hBoxText.setAlignment(Pos.CENTER);
        hBoxText.setStyle("-fx-background-color: #200f54");
        hBoxText.setPadding(new Insets( 20, 0, 0, 0 ) );
        //top,dx,down,sx

        vBox.getChildren().addAll(riga1,riga2,riga3,riga4,riga5);
    }

    public static void main(String[] args) {
        launch();
    }
}