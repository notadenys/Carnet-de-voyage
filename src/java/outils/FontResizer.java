package outils;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FontResizer {
    private FontResizer(){}

    public static void adjustFontSize(TextField textField) {
        double minFontSize = 20.0; // Minimum allowed font size
        double maxFontSize = 150.0; // Maximum allowed font size
        double width = textField.getWidth();

        double targetFontSize = calculateFontSize(textField.getText(), width, minFontSize, maxFontSize);

        textField.setFont(Font.font(textField.getFont().getName(), targetFontSize));
    }

    private static double calculateFontSize(String text, double maxWidth, double minFontSize, double maxFontSize) {
        if (text.isEmpty()) {
            return maxFontSize; // Return minimum font size for empty text
        }

        // Start with max font size and reduce until the text fits
        double fontSize = maxFontSize;
        Text tempText = new Text(text);
        tempText.setFont(Font.font(fontSize));

        while (tempText.getBoundsInLocal().getWidth() > maxWidth && fontSize > minFontSize) {
            fontSize--;
            tempText.setFont(Font.font(fontSize));
        }

        return fontSize;
    }
}
