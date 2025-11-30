package com.example.myapplication5;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    TextView resultField; // текстове поле для виводу результату
    EditText numberField; // поле для вводу числа
    TextView operationField; // текстове поле для виводу знаку операнда
    Double operand = null; // операнд операції
    String lastOperation = "="; // остання операція

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // отримуємо всі поля по id із activity_main.xml
        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);
        findViewById(R.id.add).setOnClickListener((view) -> onOperationClick("+"));
        findViewById(R.id.sub).setOnClickListener((view) -> onOperationClick("-"));
        findViewById(R.id.mul).setOnClickListener((view) -> onOperationClick("*"));
        findViewById(R.id.div).setOnClickListener((view) -> onOperationClick("/"));
        findViewById(R.id.eq).setOnClickListener((view) -> onOperationClick("="));
        findViewById(R.id.n0).setOnClickListener((view) -> onNumberClick("0"));
        findViewById(R.id.n1).setOnClickListener((view) -> onNumberClick("1"));
        findViewById(R.id.n2).setOnClickListener((view) -> onNumberClick("2"));
        findViewById(R.id.n3).setOnClickListener((view) -> onNumberClick("3"));
        findViewById(R.id.n4).setOnClickListener((view) -> onNumberClick("4"));
        findViewById(R.id.n5).setOnClickListener((view) -> onNumberClick("5"));
        findViewById(R.id.n6).setOnClickListener((view) -> onNumberClick("6"));
        findViewById(R.id.n7).setOnClickListener((view) -> onNumberClick("7"));
        findViewById(R.id.n8).setOnClickListener((view) -> onNumberClick("8"));
        findViewById(R.id.n9).setOnClickListener((view) -> onNumberClick("9"));
        findViewById(R.id.comma).setOnClickListener((view) -> onNumberClick(","));
    }

    // збереження стану
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if (operand != null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    // отримання раніше збереженого значення
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    // обробка натискання числової кнопки
    public void onNumberClick(String number) {
        numberField.append(number);
        if (lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }

    // обробка натискання кнопки операнда
    public void onOperationClick(String op) {
        String number = numberField.getText().toString();
        // якщо введено щось
        if (number.length() > 0) {
            number = number.replace(',', '.');
            try {
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException ex) {
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    private void performOperation(Double number, String operation) {
        // Якщо оперант раніше не був встановлений (при вводі самої першої опції)
        if (operand == null) {
            operand = number;
        } else {
            if (lastOperation.equals("=")) {
                lastOperation = operation;
            }
            switch (lastOperation) {
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number == 0) {
                        operand = 0.0;
                    } else {
                        operand /= number;
                    }
                    break;
                case "*":
                    operand *= number;
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;
            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }
}