/*
 * Created by JFormDesigner on Mon Oct 17 17:16:59 CST 2022
 */

package com.uhf.form;

import javax.swing.plaf.*;

import com.rscja.deviceapi.interfaces.IUHF;
import com.uhf.UHFMainForm;
import com.uhf.utils.StringUtils;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author zp
 */
public class ReadWriteForm extends JPanel {

    public ReadWriteForm() {
        initComponents();
    }

    public Map<String, Integer> bankMap = new HashMap<String, Integer>() {
        {
            put("RESERVED", IUHF.Bank_RESERVED);
            put("EPC", IUHF.Bank_EPC);
            put("TID", IUHF.Bank_TID);
            put("USER", IUHF.Bank_USER);
        }
    };

    private void btnReadActionPerformed(ActionEvent e) {
        String filterStart = txtFilterStart.getText();
        String filterLen = txtFilterLen.getText();
        String filterData = txtFilterData.getText();
        String start = txtStart.getText();
        String len = txtLen.getText();
        String result = "";

        String accessPwd = txtAccessPwd.getText();
        if (StringUtils.isEmpty(accessPwd) || accessPwd.length() != 8) {
            JOptionPane.showMessageDialog(this, "The destruction password must be 4 bytes of hexadecimal data!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (StringUtils.isEmpty(start)) {
            JOptionPane.showMessageDialog(this, "The starting address cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (StringUtils.isEmpty(len)) {
            JOptionPane.showMessageDialog(this, "Data content cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //过滤数据
        if (!StringUtils.isEmpty(filterLen) && Integer.parseInt(filterLen) > 0) {
            if (StringUtils.isEmpty(filterStart)) {
                JOptionPane.showMessageDialog(this, "Filter start address cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (StringUtils.isEmpty(filterData)) {
                JOptionPane.showMessageDialog(this, "Filter data content cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if ((filterData.length() * 4 < Integer.parseInt(filterLen))) {
                JOptionPane.showMessageDialog(this, "Filter data content and length do not match!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int filterBank = -1;
            if (rbFilterEpc.isSelected()) {
                filterBank = IUHF.Bank_EPC;
            } else if (rbFilterTid.isSelected()) {
                filterBank = IUHF.Bank_TID;
            } else if (rbFilterUser.isSelected()) {
                filterBank = IUHF.Bank_USER;
            }

            result = UHFMainForm.ur4.readData(accessPwd, filterBank, Integer.parseInt(filterStart), Integer.parseInt(filterLen), filterData,
                    bankMap.get((String) cbBank.getSelectedItem()), Integer.parseInt(start), Integer.parseInt(len));

        } else {    //不过滤
            result = UHFMainForm.ur4.readData(accessPwd, bankMap.get((String) cbBank.getSelectedItem()), Integer.parseInt(start), Integer.parseInt(len));
        }

        //输出结果
        if (result == null) {
            JOptionPane.showMessageDialog(this, "Failed to read data！", "", JOptionPane.ERROR_MESSAGE);
        } else if (result.equals("")){
            JOptionPane.showMessageDialog(this, "Read data is null！", "", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            txtData.setText(result);
        }
    }

    private void btnWriteActionPerformed(ActionEvent e) {
        String filterStart = txtFilterStart.getText();
        String filterLen = txtFilterLen.getText();
        String filterData = txtFilterData.getText();
        String start = txtStart.getText();
        String len = txtLen.getText();
        String data = txtData.getText();
        boolean result = false;

        String accessPwd = txtAccessPwd.getText();
        if (StringUtils.isEmpty(accessPwd) || accessPwd.length() != 8) {
            JOptionPane.showMessageDialog(this, "The destruction password must be 4 bytes of hexadecimal data!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (StringUtils.isEmpty(start)) {
            JOptionPane.showMessageDialog(this, "The starting address cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (StringUtils.isEmpty(len)) {
            JOptionPane.showMessageDialog(this, "Data content cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (StringUtils.isEmpty(data)) {
            JOptionPane.showMessageDialog(this, "Data content cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //过滤数据
        if (!StringUtils.isEmpty(filterLen) && Integer.parseInt(filterLen) > 0) {
            if (StringUtils.isEmpty(filterStart)) {
                JOptionPane.showMessageDialog(this, "Filter start address cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (StringUtils.isEmpty(filterData)) {
                JOptionPane.showMessageDialog(this, "Filter data content cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if ((filterData.length() * 4 < Integer.parseInt(filterLen))) {
                JOptionPane.showMessageDialog(this, "Filter data content and length do not match!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int filterBank = -1;
            if (rbFilterEpc.isSelected()) {
                filterBank = IUHF.Bank_EPC;
            } else if (rbFilterTid.isSelected()) {
                filterBank = IUHF.Bank_TID;
            } else if (rbFilterUser.isSelected()) {
                filterBank = IUHF.Bank_USER;
            }

            result = UHFMainForm.ur4.writeData(accessPwd, filterBank, Integer.parseInt(filterStart), Integer.parseInt(filterLen), filterData,
                    bankMap.get((String) cbBank.getSelectedItem()), Integer.parseInt(start), Integer.parseInt(len), data);

        } else {    //不过滤
            result = UHFMainForm.ur4.writeData(accessPwd, bankMap.get((String) cbBank.getSelectedItem()), Integer.parseInt(start), Integer.parseInt(len), data);
        }

        if (result) {
            JOptionPane.showMessageDialog(this, "Write data successfully!", "", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to write data!", "", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void rbFilterEpcActionPerformed(ActionEvent e) {
        rbFilterEpc.setSelected(true);
        rbFilterTid.setSelected(false);
        rbFilterUser.setSelected(false);
    }


    private void rbFilterTidActionPerformed(ActionEvent e) {
        rbFilterEpc.setSelected(false);
        rbFilterTid.setSelected(true);
        rbFilterUser.setSelected(false);
    }

    private void rbFilterUserActionPerformed(ActionEvent e) {
        rbFilterEpc.setSelected(false);
        rbFilterTid.setSelected(false);
        rbFilterUser.setSelected(true);
    }

    private void cbBankItemStateChanged(ItemEvent e) {
        String item = (String) e.getItem();
        switch (item) {
            case "RESERVED":
                txtStart.setText("0");
                txtLen.setText("4");
                break;
            case "EPC":
                txtStart.setText("2");
                txtLen.setText("6");
                break;
            case "TID":
            case "USER":
                txtStart.setText("0");
                txtLen.setText("6");
                break;
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel3 = new JPanel();
        label1 = new JLabel();
        scrollPane2 = new JScrollPane();
        txtFilterData = new JTextArea();
        label2 = new JLabel();
        txtFilterStart = new JTextField();
        label3 = new JLabel();
        txtFilterLen = new JTextField();
        panel4 = new JPanel();
        panel5 = new JPanel();
        rbFilterEpc = new JRadioButton();
        rbFilterTid = new JRadioButton();
        rbFilterUser = new JRadioButton();
        label8 = new JLabel();
        label9 = new JLabel();
        panel1 = new JPanel();
        cbBank = new JComboBox<>();
        label4 = new JLabel();
        label5 = new JLabel();
        txtStart = new JTextField();
        txtLen = new JTextField();
        label6 = new JLabel();
        label7 = new JLabel();
        txtData = new JTextField();
        btnRead = new JButton();
        btnWrite = new JButton();
        pass = new JLabel();
        txtAccessPwd = new JTextField();

        //======== this ========
        setPreferredSize(new Dimension(1090, 599));
        setLayout(null);

        //======== panel3 ========
        {
            panel3.setBackground(new Color(238, 238, 238));
            panel3.setBorder(new TitledBorder("Filter"));
            panel3.setLayout(null);

            //---- label1 ----
            label1.setText("Data");
            panel3.add(label1);
            label1.setBounds(new Rectangle(new Point(10, 30), label1.getPreferredSize()));

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(txtFilterData);
            }
            panel3.add(scrollPane2);
            scrollPane2.setBounds(65, 15, 330, 50);

            //---- label2 ----
            label2.setText("Ptr:");
            panel3.add(label2);
            label2.setBounds(new Rectangle(new Point(425, 35), label2.getPreferredSize()));
            panel3.add(txtFilterStart);
            txtFilterStart.setBounds(460, 25, 65, 35);

            //---- label3 ----
            label3.setText("Len:");
            panel3.add(label3);
            label3.setBounds(new Rectangle(new Point(560, 35), label3.getPreferredSize()));
            panel3.add(txtFilterLen);
            txtFilterLen.setBounds(590, 25, 60, 35);

            //======== panel4 ========
            {
                panel4.setMinimumSize(new Dimension(30, 10));
                panel4.setLayout(new BorderLayout());
            }
            panel3.add(panel4);
            panel4.setBounds(730, 30, panel4.getPreferredSize().width, 0);

            //======== panel5 ========
            {
                panel5.setPreferredSize(new Dimension(10, 50));
                panel5.setBorder(LineBorder.createBlackLineBorder());
                panel5.setToolTipText("\u8fc7\u6ee4");
                panel5.setLayout(null);

                //---- rbFilterEpc ----
                rbFilterEpc.setText("EPC");
                rbFilterEpc.addActionListener(e -> rbFilterEpcActionPerformed(e));
                panel5.add(rbFilterEpc);
                rbFilterEpc.setBounds(new Rectangle(new Point(5, 15), rbFilterEpc.getPreferredSize()));

                //---- rbFilterTid ----
                rbFilterTid.setText("Tid");
                rbFilterTid.addActionListener(e -> rbFilterTidActionPerformed(e));
                panel5.add(rbFilterTid);
                rbFilterTid.setBounds(new Rectangle(new Point(75, 15), rbFilterTid.getPreferredSize()));

                //---- rbFilterUser ----
                rbFilterUser.setText("User");
                rbFilterUser.addActionListener(e -> rbFilterUserActionPerformed(e));
                panel5.add(rbFilterUser);
                rbFilterUser.setBounds(new Rectangle(new Point(140, 15), rbFilterUser.getPreferredSize()));

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel5.getComponentCount(); i++) {
                        Rectangle bounds = panel5.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel5.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel5.setMinimumSize(preferredSize);
                    panel5.setPreferredSize(preferredSize);
                }
            }
            panel3.add(panel5);
            panel5.setBounds(700, 20, 215, panel5.getPreferredSize().height);

            //---- label8 ----
            label8.setText("bit");
            panel3.add(label8);
            label8.setBounds(new Rectangle(new Point(525, 35), label8.getPreferredSize()));

            //---- label9 ----
            label9.setText("bit");
            panel3.add(label9);
            label9.setBounds(new Rectangle(new Point(650, 35), label9.getPreferredSize()));

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel3.getComponentCount(); i++) {
                    Rectangle bounds = panel3.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel3.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel3.setMinimumSize(preferredSize);
                panel3.setPreferredSize(preferredSize);
            }
        }
        add(panel3);
        panel3.setBounds(10, 15, 945, 80);

        //======== panel1 ========
        {
            panel1.setPreferredSize(new Dimension(22, 40));
            panel1.setBorder(LineBorder.createBlackLineBorder());
            panel1.setLayout(null);

            //---- cbBank ----
            cbBank.setModel(new DefaultComboBoxModel<>(new String[] {
                "RESERVED",
                "EPC",
                "TID",
                "USER"
            }));
            cbBank.addItemListener(e -> cbBankItemStateChanged(e));
            panel1.add(cbBank);
            cbBank.setBounds(95, 10, 215, 30);

            //---- label4 ----
            label4.setText("Bank:");
            panel1.add(label4);
            label4.setBounds(new Rectangle(new Point(15, 15), label4.getPreferredSize()));

            //---- label5 ----
            label5.setText("Ptr:");
            panel1.add(label5);
            label5.setBounds(new Rectangle(new Point(15, 105), label5.getPreferredSize()));

            //---- txtStart ----
            txtStart.setText("0");
            panel1.add(txtStart);
            txtStart.setBounds(95, 100, 215, 30);

            //---- txtLen ----
            txtLen.setText("4");
            panel1.add(txtLen);
            txtLen.setBounds(95, 145, 215, 30);

            //---- label6 ----
            label6.setText("Len:");
            panel1.add(label6);
            label6.setBounds(new Rectangle(new Point(15, 150), label6.getPreferredSize()));

            //---- label7 ----
            label7.setText("Data:");
            panel1.add(label7);
            label7.setBounds(15, 200, 45, 17);
            panel1.add(txtData);
            txtData.setBounds(95, 195, 215, 90);

            //---- btnRead ----
            btnRead.setText("READ");
            btnRead.addActionListener(e -> btnReadActionPerformed(e));
            panel1.add(btnRead);
            btnRead.setBounds(new Rectangle(new Point(85, 295), btnRead.getPreferredSize()));

            //---- btnWrite ----
            btnWrite.setText("WRITE");
            btnWrite.addActionListener(e -> btnWriteActionPerformed(e));
            panel1.add(btnWrite);
            btnWrite.setBounds(new Rectangle(new Point(225, 295), btnWrite.getPreferredSize()));

            //---- pass ----
            pass.setText("Access Pwd:");
            panel1.add(pass);
            pass.setBounds(new Rectangle(new Point(15, 65), pass.getPreferredSize()));

            //---- txtAccessPwd ----
            txtAccessPwd.setText("00000000");
            panel1.add(txtAccessPwd);
            txtAccessPwd.setBounds(95, 60, 215, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel1.getComponentCount(); i++) {
                    Rectangle bounds = panel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1.setMinimumSize(preferredSize);
                panel1.setPreferredSize(preferredSize);
            }
        }
        add(panel1);
        panel1.setBounds(10, 115, 395, 345);

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
    private JPanel panel3;
    private JLabel label1;
    private JScrollPane scrollPane2;
    private JTextArea txtFilterData;
    private JLabel label2;
    private JTextField txtFilterStart;
    private JLabel label3;
    private JTextField txtFilterLen;
    private JPanel panel4;
    private JPanel panel5;
    private JRadioButton rbFilterEpc;
    private JRadioButton rbFilterTid;
    private JRadioButton rbFilterUser;
    private JLabel label8;
    private JLabel label9;
    private JPanel panel1;
    private JComboBox<String> cbBank;
    private JLabel label4;
    private JLabel label5;
    private JTextField txtStart;
    private JTextField txtLen;
    private JLabel label6;
    private JLabel label7;
    private JTextField txtData;
    private JButton btnRead;
    private JButton btnWrite;
    private JLabel pass;
    private JTextField txtAccessPwd;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
