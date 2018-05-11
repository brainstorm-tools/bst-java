
package org.brainstorm.html;

import javax.swing.JEditorPane;
import javax.swing.JFrame;


public class ExampleInlineImage {

    public ExampleInlineImage() {
        JFrame jf = new JFrame();
	JEditorPane jep = new JEditorPane();
        jep.setEditable(false);
	jep.setContentType("text/html");
	jep.setEditorKit(new BstHTMLEditorKit());
	jep.setText(
                  "<html><body>test<br><img src=\"data:image/png;base64,iVBORw"
                + "0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAACuklEQVR42sWVv09TU"
                + "RTH71AXE40xaRdXEwbD4ODiIFr6EwcXN1cnR0cXoyTWUjcH3n0dDG4aEwca"
                + "oqggWn8ERWtoKdVBoA2lxbj4BxzPt+eaPEn7nhDeleRDTr733vP9vtvXU0V"
                + "E6n8i//79L5KdUKVUXtHobUXxnIAaGtawZzcNdxvgEIw2u0Va39SMa9A9DW"
                + "vYE2aAKJ4WhpPTipySgBoa1rAnzAAxXPnalkvOjCL3iYAaGtawJ/QA650ia"
                + "TYuPhVQQ7MbAOazAmqrAdb4hXOe8fW/EFBDsxdgmwM8Z/M5ATU0ewF+cACY"
                + "vxRQQ7MS4NwtRd9xA/NsviCghoa1vQaIjP2ZcLnBxNng9E1F37p3yYH5awE"
                + "1NKxhj18PeIx5J6YJ0Jtwaz/vUKObZwoDqW8VaL5xkfQr/gaUBdTQsOZ3Fr"
                + "3h8dfENAGiCU5X79ygSW7oBKDf8JO/ZXMDamhB59AbHomcZ2KaADGIy+1r5"
                + "HAjjabv+lMMYNA59ERveCS8L6s3QKV9lRxsfs+H9hn0RG94DAyw1L5CepEP"
                + "fOCn8cFd7I/vGQa94dE3QJpfjMr2ZZr6oui+D/c+c8OP0tALNKz5nUVveKT"
                + "zfQKcL6hfmQnVWxxEin9uR8YVlVuXSC+xsQE1NKxhj18PeCSuq/LOl/AIc5"
                + "KJM6M+ZHB9C60LpD+xuQE1NHO1mYAeI8yJnV/DAyZELIChJJvMtVKkK2xuQ"
                + "A0tKQGGAnpEjXlkT6M4xSazzTPk8ufpBVrKxm8BTGaap8itsvGyoSqatQCl"
                + "jWFya2LcoyaatQCPW8dJr/BnXzOsiGYtwKPmMdKrbFw3rIpmJUCaTR60jlL"
                + "xK199Q0ANLW0jQJaHycPOQZpq8mTbMDRFy+bDDxDNjKsqjPC0XqAlvRMupA"
                + "CHmWHmbOCECylAxISIBk64kALs+99vkl5XSNCDZhMAAAAASUVORK5CYII="
                + "\"></body></html>");
        
        jf.getRootPane().setContentPane(jep);
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    } // End constructor...
    
    public static void main(String[] args) {
        ExampleInlineImage app = new ExampleInlineImage();
    } // End main()...
    
} // End ExpInlineImage{}...
