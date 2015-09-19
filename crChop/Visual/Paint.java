package org.crChop.Visual;

import org.crChop.Enums.Tree;
import org.crChop.Variables.Widget;
import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

import java.awt.*;

/**
 * Created by Dakota on 9/7/2015.
 */
public class Paint extends ClientAccessor implements PaintListener {
    public static String status = "";
    public static int width, height;
    private int startLevel, startExperience, logs, dataCount = 1;
    private boolean hideName;
    private String tree = "";
    private Color bg = new Color(0, 0, 0, 150), paint = Color.white;

    public Paint(ClientContext ctx, int startLevel, int startExperience, Tree tree, int logs, boolean hideName) {
        super(ctx);
        this.startLevel = startLevel;
        this.startExperience = startExperience;
        this.tree = tree.getName();
        this.logs = logs;
        this.hideName = hideName;
    }

    public void repaint(Graphics g) {
        PaintMethods PaintMethods = new PaintMethods(ctx, this.startLevel, this.startExperience, this.logs);
        long runtime = ctx.controller.script().getTotalRuntime();
        final Graphics2D g2 = (Graphics2D) g;
        FontMetrics fm = g2.getFontMetrics();
        int textOffset = fm.getHeight();


        // SCRIPT PAINT
        // ============
        if (hideName) {
            g2.setColor(Color.black);
            g2.fill(new Rectangle(Widget.nameWidget.screenPoint().x, Widget.nameWidget.screenPoint().y, fm.stringWidth(Widget.nameWidget.text().split(":")[0]), Widget.nameWidget.height()));
        }

        PaintMethods.borderedRect(2, 2, width, height, paint, bg, g2);
        g2.setColor(paint);

        // Paint Title
        PaintMethods.stringTitle("org.crChop - " + PaintMethods.formatTime(runtime), width + 1, g2);

        // Level Information
        g2.drawString("Level: " + ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING) + " (+" + PaintMethods.levelsGained() + ")", 5, textOffset * ++dataCount);

        // Experience Information
        g2.drawString("Exp: " + PaintMethods.formatLetter(PaintMethods.experienceGained()) + " (" + PaintMethods.formatLetter(PaintMethods.hourlyExperience()) + " /hr)", 5, textOffset * ++dataCount);

        // Logs Cut
        g2.drawString(tree + "s: " + logs + " (" + PaintMethods.logsPerHour() + " /hr)", 5, textOffset * ++dataCount);

        // Time Till Level
        g2.drawString("Leveling in: " + PaintMethods.timeTillLevel(), 5, textOffset * ++dataCount);

        // Time Till Max
        g2.drawString("Maxing in: " + PaintMethods.timeTillMax(), 5, textOffset * ++dataCount);

        width = 125;
        height = (textOffset * dataCount) + 2;

//        for (GameObject t : ctx.objects.select().name(tree.getName()).within(PaintMethods.mapArea()).limit(10)) {
//            Point p = t.tile().matrix(ctx).mapPoint();
//            g2.setColor(bg);
//            g2.fillRect(p.x + 8, p.y - 2, fm.stringWidth(t.name() + " (" + (int) ctx.players.local().tile().distanceTo(t.tile()) + ")") + 2, textOffset - 2);
//            PaintMethods.shadowString(t.name() + " (" + (int) ctx.players.local().tile().distanceTo(t.tile()) + ")", p.x + 10, p.y + (textOffset / 2 + 1), Color.cyan, g2);
//            g2.setColor(Color.cyan);
//            g2.fillOval(p.x, p.y, 10, 10);
//        }
    }
}