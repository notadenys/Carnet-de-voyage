package outils;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FontResizer {
    private FontResizer(){}

    /**
     * Adjusts font size based on the TextField width
     * @param textField TextField that is being changed
     * @param minFontSize font size won't get smaller than this point
     * @param maxFontSize font size won't get bigger than this point
     */
    public static void adjustFontSize(TextField textField, double minFontSize, double maxFontSize) {
        double width = textField.getWidth();

        double targetFontSize = calculateFontSize(textField.getText(), width, minFontSize, maxFontSize);

        textField.setFont(Font.font(textField.getFont().getName(), targetFontSize));
    }

    /**
     * Allows to calculate the font size based on the TextField width
     * @param text String to calculate the size
     * @param maxWidth maximal width of the TextField
     * @param minFontSize font size won't get smaller than this point
     * @param maxFontSize font size won't get bigger than this point
     * @return calculated font size
     */
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
