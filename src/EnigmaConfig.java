import enigma.console.TextAttributes;
import enigma.console.TextWindow;
import enigma.core.Enigma;
import enigma.event.TextMouseListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EnigmaConfig {

    public static enigma.console.Console cn = Enigma.getConsole("Text Editor", 100, 40, 16);
    public static TextWindow cnt = cn.getTextWindow();
    public static TextAttributes att0 = new TextAttributes(Color.white, Color.black); // foreground, background color
    public static TextAttributes att1 = new TextAttributes(Color.black, Color.white);
    public static TextAttributes att2 = new TextAttributes(Color.black, Color.red);
    public TextMouseListener tmlis;
    public static KeyListener klis;

    public EnigmaConfig() {
        cn.getTextWindow();

        // ------ Standard code for mouse and keyboard ------ Do not change
        klis = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (SD.keypr == 0) {
                    SD.keypr = 1;
                    SD.rkey = e.getKeyCode();
                    SD.rkeymod = e.getModifiersEx();
                    if (SD.rkey == KeyEvent.VK_CAPS_LOCK) {
                        if (SD.capslock == 0)
                            SD.capslock = 1;
                        else
                            SD.capslock = 0;
                    }
                }
            }
            public void keyReleased(KeyEvent e) {}
        };
        cn.getTextWindow().addKeyListener(klis);
        // --------------------------------------------------------------------------

        cnt.setCursorType(1); // 1 -> cursor visible
        cn.setTextAttributes(att0);
    } // editor config


}