package game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Κλάση για περιγραφή οδηγιών
 *
 * @author Team Hack-You
 */
public class Guide extends UtilityFrame{

    Menu menu;
    Options options;

    /**
     * Κατασκευαστής που καλείται όταν το guide ανοίγει από το παράθυρο options
     * @param options : Το παράθυρο options από το οποίο κλήθηκε ο guide
     */
    public Guide(Options options) {
        super("Guide",800,800);
        this.options = options;
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                options.showGuide.setEnabled(true);
                Options.guideOpen = false;
                frame.dispose();
            }
        });
        frame.add(backgroundLabel);

    }

    /**
     * Κατασκευαστής που καλείται όταν το guide ανοίγει από το παράθυρο menu
     * @param menu : Το παράθυρο menu από το οποίο κλήθηκε ο guide
     */
    public Guide(Menu menu) {
        super("Guide",800,800);
        this.menu = menu;
        super.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menu.how2play.setEnabled(true);
                Guide.super.frame.dispose();
            }
        });
        frame.add(backgroundLabel);
    }

}