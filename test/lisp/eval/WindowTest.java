package lisp.eval;

import org.junit.Test;

public class WindowTest {
    @Test
    public void Windowのテスト(){
        Window window = new Window(480,480);
        window.drawLine(100,100,200,100,0);
        window.drawLine(200,100,200,200,1);
        window.drawLine(200,200,100,200,2);
        window.drawLine(100,200,100,100,3);

    }
}
