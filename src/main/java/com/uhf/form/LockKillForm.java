/*
 * Created by JFormDesigner on Mon Oct 17 17:19:26 CST 2022
 */

package com.uhf.form;

import com.rscja.deviceapi.interfaces.IUHF;
import com.uhf.UHFMainForm;
import com.uhf.utils.StringUtils;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;

/**
 * @author zp
 */
public class LockKillForm extends JPanel {
    public LockKillForm() {
        initComponents();
    }

    /**
     * 锁标签
     *
     * @param e
     */
    private void btnLockActionPerformed(ActionEvent e) {

        String start = txtFilterStart.getText();
        String len = txtFilterLen.getText();
        String data = txtFilterData.getText();

        String temp = lblLockCode.getText().trim();
        if (temp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "The lock code cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //过滤数据
        if (!StringUtils.isEmpty(len) && Integer.parseInt(len) > 0) {
            if (StringUtils.isEmpty(start)) {
                JOptionPane.showMessageDialog(this, "The starting address cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (StringUtils.isEmpty(data)) {
                JOptionPane.showMessageDialog(this, "Data length cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if ((data.length() * 4 < Integer.parseInt(len))) {
                JOptionPane.showMessageDialog(this, "Data content and length do not match!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String accessPwd = txtAccessPwd.getText();
            if (StringUtils.isEmpty(accessPwd) || accessPwd.length() != 8) {
                JOptionPane.showMessageDialog(this, "The access password must be 4-byte hexadecimal data!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int bank = -1;
            if (rbFilterEpc.isSelected()) {
                bank = IUHF.Bank_EPC;
            } else if (rbFliterTid.isSelected()) {
                bank = IUHF.Bank_TID;
            } else if (rbFilterUser.isSelected()) {
                bank = IUHF.Bank_USER;
            }

            boolean result = UHFMainForm.ur4.lockMem(accessPwd, bank, Integer.parseInt(start), Integer.parseInt(len), data, lblLockCode.getText());
            if (result) {
                JOptionPane.showMessageDialog(this, "Lock succeeded!", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Lock failed!", "", JOptionPane.ERROR_MESSAGE);
            }
            return;
        } else {
            //不过滤
            String accessPwd = txtAccessPwd.getText();
            if (StringUtils.isEmpty(accessPwd) || accessPwd.length() != 8) {
                JOptionPane.showMessageDialog(this, "The access password must be 4-byte hexadecimal data!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean result = UHFMainForm.ur4.lockMem(accessPwd, lblLockCode.getText());
            if (result) {
                JOptionPane.showMessageDialog(this, "Lock succeeded!", "", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Lock failed!", "", JOptionPane.ERROR_MESSAGE);
            }
        }


    }

    private void generateLockCode() {
        //*****************获取锁定码****************
        int lockMode = 0;
        if (rbLock.isSelected()) {
            lockMode = IUHF.LockMode_LOCK;
        } else if (rbPLock.isSelected()) {
            lockMode = IUHF.LockMode_PLOCK;
        } else if (rbOpen.isSelected()) {
            lockMode = IUHF.LockMode_OPEN;
        } else if (rbPOpen.isSelected()) {
            lockMode = IUHF.LockMode_POPEN;
        } else {
            lblLockCode.setText("");
            return;
        }

        ArrayList<Integer> lockBank = new ArrayList<>();
        if (rbAccessPwd.isSelected()) {
            lockBank.add(IUHF.LockBank_ACCESS);
        }
        if (rbKillPwd.isSelected()) {
            lockBank.add(IUHF.LockBank_KILL);
        }
        if (rbEpc.isSelected()) {
            lockBank.add(IUHF.LockBank_EPC);
        }
        if (rbTid.isSelected()) {
            lockBank.add(IUHF.LockBank_TID);
        }
        if (rbUser.isSelected()) {
            lockBank.add(IUHF.LockBank_USER);
        }
        if (lockBank.size() == 0) {
            lblLockCode.setText("");
            return;
        }

        String hexLockCode = UHFMainForm.ur4.generateLockCode(lockBank, lockMode);
        lblLockCode.setText(hexLockCode);
        //********************************
    }

    private void rbOpenActionPerformed(ActionEvent e) {
        rbOpen.setSelected(true);
        rbLock.setSelected(false);
        rbPOpen.setSelected(false);
        rbPLock.setSelected(false);
        generateLockCode();
    }

    private void rbLockActionPerformed(ActionEvent e) {
        rbOpen.setSelected(false);
        rbLock.setSelected(true);
        rbPOpen.setSelected(false);
        rbPLock.setSelected(false);
        generateLockCode();
    }

    private void rbPOpenActionPerformed(ActionEvent e) {
        rbOpen.setSelected(false);
        rbLock.setSelected(false);
        rbPOpen.setSelected(true);
        rbPLock.setSelected(false);
        generateLockCode();
    }

    private void rbPLockActionPerformed(ActionEvent e) {
        rbOpen.setSelected(false);
        rbLock.setSelected(false);
        rbPOpen.setSelected(false);
        rbPLock.setSelected(true);
        generateLockCode();
    }

    private void rbKillPwdActionPerformed(ActionEvent e) {
        rbKillPwd.setSelected(rbKillPwd.isSelected());
        generateLockCode();
    }

    private void rbAccessPwdActionPerformed(ActionEvent e) {
        rbAccessPwd.setSelected(rbAccessPwd.isSelected());
        generateLockCode();
    }

    private void rbEpcActionPerformed(ActionEvent e) {
        rbEpc.setSelected(rbEpc.isSelected());
        generateLockCode();
    }

    private void rbTidActionPerformed(ActionEvent e) {
        rbTid.setSelected(rbTid.isSelected());
        generateLockCode();
    }

    private void rbUserActionPerformed(ActionEvent e) {
        rbUser.setSelected(rbUser.isSelected());
        generateLockCode();
    }

    private void btnKillActionPerformed(ActionEvent e) {
        boolean result = false;
        String filterStart = txtFilterStart.getText();
        String filterLen = txtFilterLen.getText();
        String filterData = txtFilterData.getText();
        String killPwd = txtKillPwd.getText();

        if (StringUtils.isEmpty(killPwd) || killPwd.length() != 8) {
            JOptionPane.showMessageDialog(this, "The destruction password must be 4 bytes of hexadecimal data!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //过滤数据
        if (!StringUtils.isEmpty(filterLen) && Integer.parseInt(filterLen) > 0) {
            if (StringUtils.isEmpty(filterStart)) {
                JOptionPane.showMessageDialog(this, "The starting address cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (StringUtils.isEmpty(filterData)) {
                JOptionPane.showMessageDialog(this, "Data content cannot be empty!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if ((filterData.length() * 4 < Integer.parseInt(filterLen))) {
                JOptionPane.showMessageDialog(this, "Data content and length do not match!", "", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int bank = -1;
            if (rbFilterEpc.isSelected()) {
                bank = IUHF.Bank_EPC;
            } else if (rbFliterTid.isSelected()) {
                bank = IUHF.Bank_TID;
            } else if (rbFilterUser.isSelected()) {
                bank = IUHF.Bank_USER;
            }

            result = UHFMainForm.ur4.killTag(killPwd, bank, Integer.parseInt(filterStart), Integer.parseInt(filterLen), filterData);

        } else {    //不过滤
            result = UHFMainForm.ur4.killTag(killPwd);
        }

        if (result) {
            JOptionPane.showMessageDialog(this, "Destruction succeeded!", "", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Destruction failed!", "", JOptionPane.ERROR_MESSAGE);
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
        rbFliterTid = new JRadioButton();
        rbFilterUser = new JRadioButton();
        panel6 = new JPanel();
        panel7 = new JPanel();
        label4 = new JLabel();
        txtAccessPwd = new JTextField();
        label5 = new JLabel();
        panel10 = new JPanel();
        rbOpen = new JRadioButton();
        rbLock = new JRadioButton();
        rbPOpen = new JRadioButton();
        rbPLock = new JRadioButton();
        btnLock = new JButton();
        panel11 = new JPanel();
        rbKillPwd = new JRadioButton();
        rbAccessPwd = new JRadioButton();
        rbEpc = new JRadioButton();
        rbTid = new JRadioButton();
        rbUser = new JRadioButton();
        label8 = new JLabel();
        lblLockCode = new JLabel();
        panel8 = new JPanel();
        panel9 = new JPanel();
        label6 = new JLabel();
        txtKillPwd = new JTextField();
        label7 = new JLabel();
        btnKill = new JButton();

        //======== this ========
        setLayout(null);

        //======== panel3 ========
        {
            panel3.setBackground(new Color(238, 238, 238));
            panel3.setBorder(new TitledBorder("Filter"));
            panel3.setLayout(null);

            //---- label1 ----
            label1.setText("Data:");
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
            label2.setBounds(new Rectangle(new Point(405, 35), label2.getPreferredSize()));
            panel3.add(txtFilterStart);
            txtFilterStart.setBounds(460, 30, 65, txtFilterStart.getPreferredSize().height);

            //---- label3 ----
            label3.setText("Len");
            panel3.add(label3);
            label3.setBounds(new Rectangle(new Point(535, 35), label3.getPreferredSize()));
            panel3.add(txtFilterLen);
            txtFilterLen.setBounds(565, 30, 60, txtFilterLen.getPreferredSize().height);

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
                panel5.add(rbFilterEpc);
                rbFilterEpc.setBounds(new Rectangle(new Point(5, 15), rbFilterEpc.getPreferredSize()));

                //---- rbFliterTid ----
                rbFliterTid.setText("Tid");
                panel5.add(rbFliterTid);
                rbFliterTid.setBounds(new Rectangle(new Point(75, 15), rbFliterTid.getPreferredSize()));

                //---- rbFilterUser ----
                rbFilterUser.setText("User");
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
            panel5.setBounds(635, 20, 215, panel5.getPreferredSize().height);

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
        panel3.setBounds(10, 15, 875, 80);

        //======== panel6 ========
        {
            panel6.setBackground(new Color(238, 238, 238));
            panel6.setBorder(new TitledBorder("Lock"));
            panel6.setLayout(null);

            //======== panel7 ========
            {
                panel7.setMinimumSize(new Dimension(30, 10));
                panel7.setLayout(new BorderLayout());
            }
            panel6.add(panel7);
            panel7.setBounds(730, 30, panel7.getPreferredSize().width, 0);

            //---- label4 ----
            label4.setText("Access Pwd:");
            panel6.add(label4);
            label4.setBounds(20, 35, 80, label4.getPreferredSize().height);
            panel6.add(txtAccessPwd);
            txtAccessPwd.setBounds(95, 30, 170, 30);

            //---- label5 ----
            label5.setText("(Default password cannot be used)  ");
            panel6.add(label5);
            label5.setBounds(270, 35, 250, 17);

            //======== panel10 ========
            {
                panel10.setPreferredSize(new Dimension(10, 50));
                panel10.setBorder(LineBorder.createBlackLineBorder());
                panel10.setToolTipText("\u8fc7\u6ee4");
                panel10.setLayout(null);

                //---- rbOpen ----
                rbOpen.setText("Open");
                rbOpen.addActionListener(e -> rbOpenActionPerformed(e));
                panel10.add(rbOpen);
                rbOpen.setBounds(5, 15, 70, rbOpen.getPreferredSize().height);

                //---- rbLock ----
                rbLock.setText("Lock");
                rbLock.addActionListener(e -> rbLockActionPerformed(e));
                panel10.add(rbLock);
                rbLock.setBounds(75, 15, 55, rbLock.getPreferredSize().height);

                //---- rbPOpen ----
                rbPOpen.setText("Permanent Opening");
                rbPOpen.addActionListener(e -> rbPOpenActionPerformed(e));
                panel10.add(rbPOpen);
                rbPOpen.setBounds(140, 15, 140, rbPOpen.getPreferredSize().height);

                //---- rbPLock ----
                rbPLock.setText("Permanent lock  ");
                rbPLock.addActionListener(e -> rbPLockActionPerformed(e));
                panel10.add(rbPLock);
                rbPLock.setBounds(285, 15, 125, 21);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel10.getComponentCount(); i++) {
                        Rectangle bounds = panel10.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel10.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel10.setMinimumSize(preferredSize);
                    panel10.setPreferredSize(preferredSize);
                }
            }
            panel6.add(panel10);
            panel10.setBounds(25, 75, 420, 50);

            //---- btnLock ----
            btnLock.setText("LOCK");
            btnLock.addActionListener(e -> btnLockActionPerformed(e));
            panel6.add(btnLock);
            btnLock.setBounds(170, 235, 150, 50);

            //======== panel11 ========
            {
                panel11.setPreferredSize(new Dimension(10, 50));
                panel11.setBorder(LineBorder.createBlackLineBorder());
                panel11.setToolTipText("\u8fc7\u6ee4");
                panel11.setLayout(null);

                //---- rbKillPwd ----
                rbKillPwd.setText("Kill-pwd");
                rbKillPwd.addActionListener(e -> rbKillPwdActionPerformed(e));
                panel11.add(rbKillPwd);
                rbKillPwd.setBounds(5, 15, 85, rbKillPwd.getPreferredSize().height);

                //---- rbAccessPwd ----
                rbAccessPwd.setText("Access-pwd");
                rbAccessPwd.addActionListener(e -> rbAccessPwdActionPerformed(e));
                panel11.add(rbAccessPwd);
                rbAccessPwd.setBounds(95, 15, 115, rbAccessPwd.getPreferredSize().height);

                //---- rbEpc ----
                rbEpc.setText("EPC");
                rbEpc.addActionListener(e -> rbEpcActionPerformed(e));
                panel11.add(rbEpc);
                rbEpc.setBounds(215, 15, 60, rbEpc.getPreferredSize().height);

                //---- rbTid ----
                rbTid.setText("TID");
                rbTid.addActionListener(e -> rbTidActionPerformed(e));
                panel11.add(rbTid);
                rbTid.setBounds(275, 15, 60, 21);

                //---- rbUser ----
                rbUser.setText("USER");
                rbUser.addActionListener(e -> rbUserActionPerformed(e));
                panel11.add(rbUser);
                rbUser.setBounds(335, 15, 75, 21);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel11.getComponentCount(); i++) {
                        Rectangle bounds = panel11.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel11.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel11.setMinimumSize(preferredSize);
                    panel11.setPreferredSize(preferredSize);
                }
            }
            panel6.add(panel11);
            panel11.setBounds(25, 140, 420, 50);

            //---- label8 ----
            label8.setText("LockCode:");
            panel6.add(label8);
            label8.setBounds(35, 205, 80, label8.getPreferredSize().height);

            //---- lblLockCode ----
            lblLockCode.setText(" ");
            panel6.add(lblLockCode);
            lblLockCode.setBounds(110, 205, 130, lblLockCode.getPreferredSize().height);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel6.getComponentCount(); i++) {
                    Rectangle bounds = panel6.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel6.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel6.setMinimumSize(preferredSize);
                panel6.setPreferredSize(preferredSize);
            }
        }
        add(panel6);
        panel6.setBounds(10, 105, 525, 295);

        //======== panel8 ========
        {
            panel8.setBackground(new Color(238, 238, 238));
            panel8.setBorder(new TitledBorder("Destruction"));
            panel8.setLayout(null);

            //======== panel9 ========
            {
                panel9.setMinimumSize(new Dimension(30, 10));
                panel9.setLayout(new BorderLayout());
            }
            panel8.add(panel9);
            panel9.setBounds(730, 30, panel9.getPreferredSize().width, 0);

            //---- label6 ----
            label6.setText("Destroy Password :");
            panel8.add(label6);
            label6.setBounds(20, 40, 125, 17);
            panel8.add(txtKillPwd);
            txtKillPwd.setBounds(150, 35, 230, 30);

            //---- label7 ----
            label7.setText("(Default password cannot be used)  ");
            panel8.add(label7);
            label7.setBounds(165, 70, 245, 17);

            //---- btnKill ----
            btnKill.setText("Destruction");
            btnKill.addActionListener(e -> btnKillActionPerformed(e));
            panel8.add(btnKill);
            btnKill.setBounds(130, 115, 150, 50);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel8.getComponentCount(); i++) {
                    Rectangle bounds = panel8.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel8.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel8.setMinimumSize(preferredSize);
                panel8.setPreferredSize(preferredSize);
            }
        }
        add(panel8);
        panel8.setBounds(560, 105, 425, 290);

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
    private JRadioButton rbFliterTid;
    private JRadioButton rbFilterUser;
    private JPanel panel6;
    private JPanel panel7;
    private JLabel label4;
    private JTextField txtAccessPwd;
    private JLabel label5;
    private JPanel panel10;
    private JRadioButton rbOpen;
    private JRadioButton rbLock;
    private JRadioButton rbPOpen;
    private JRadioButton rbPLock;
    private JButton btnLock;
    private JPanel panel11;
    private JRadioButton rbKillPwd;
    private JRadioButton rbAccessPwd;
    private JRadioButton rbEpc;
    private JRadioButton rbTid;
    private JRadioButton rbUser;
    private JLabel label8;
    private JLabel lblLockCode;
    private JPanel panel8;
    private JPanel panel9;
    private JLabel label6;
    private JTextField txtKillPwd;
    private JLabel label7;
    private JButton btnKill;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
