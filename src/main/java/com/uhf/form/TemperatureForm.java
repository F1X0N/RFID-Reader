/*
 * Created by JFormDesigner on Mon Oct 17 17:21:59 CST 2022
 */

package com.uhf.form;

import com.uhf.UHFMainForm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author zgr
 */
public class TemperatureForm extends JPanel {
    public TemperatureForm() {
        initComponents();
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) {
            int temperature = UHFMainForm.ur4.getTemperature();
//        System.out.println(temperature);
            lTemperature.setText(temperature + "℃");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        lTemperature = new JLabel();

        //======== this ========
        setLayout(null);

        //---- label1 ----
        label1.setText("Temperature:");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 5f));
        add(label1);
        label1.setBounds(new Rectangle(new Point(25, 45), label1.getPreferredSize()));

        //---- lTemperature ----
        lTemperature.setText("0");
        lTemperature.setFont(lTemperature.getFont().deriveFont(lTemperature.getFont().getSize() + 5f));
        add(lTemperature);
        lTemperature.setBounds(155, 45, 65, lTemperature.getPreferredSize().height);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JLabel lTemperature;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
