package MySweeper;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class MySweeperCell extends JButton{
	
	private int x;
	private int y;
	
	public MySweeperCell(int x, int y) {
		super();
		addMouseListener(new MouseAdapter() {
			boolean pressed;

            @Override
            public void mousePressed(MouseEvent e) {
                getModel().setArmed(true);
                getModel().setPressed(true);
                pressed = true;
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                //if(isRightButtonPressed) {underlyingButton.getModel().setPressed(true));
                getModel().setArmed(false);
                getModel().setPressed(false);

                if (pressed) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        setBackground(new Color(200, 0, 40));
                    }
                    else {
                    	MySweeper.onClick(x, y, true);
                    }
                }
                pressed = false;

            }
		});
	}
}
