/*
 * Created by JFormDesigner on Mon Oct 17 16:34:19 CST 2022
 */

package com.uhf;

import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.plaf.*;

import com.rscja.deviceapi.RFIDWithUHFNetworkUR4;
import com.rscja.deviceapi.RFIDWithUHFSerialPortUR4;
import com.rscja.deviceapi.interfaces.IUR4;
import com.uhf.form.*;
import com.uhf.utils.StringUtils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author zp
 */
public class UHFMainForm extends JFrame {

    public static void main(String[] args) {
        UHFMainForm frame= new UHFMainForm();
        frame.setVisible(true);
    }

    public static IUR4 ur4=null;
    RFIDWithUHFNetworkUR4 ur4Network=null;
    RFIDWithUHFSerialPortUR4 ur4SerialPort=null;

    ArrayList<JPanel> formList=new ArrayList<JPanel>();
    InventoryForm inventoryForm=new InventoryForm();
    ReadWriteForm readWriteForm=new ReadWriteForm();
    ConfigForm configForm=new ConfigForm();
    LockKillForm lockKillForm=new LockKillForm();
    UHFInfoForm uhfInfoForm=new UHFInfoForm();
    TemperatureForm temperatureForm=new TemperatureForm();
    FirmwareUpgradeForm upgradeForm=new FirmwareUpgradeForm();

    public UHFMainForm() {
        initComponents();
        initUI();
    }

    private void initUI(){
        tabPane.addTab("Inventory", null, inventoryForm, null);
        tabPane.addTab("ReadAndWrite", null, readWriteForm, null);
        tabPane.addTab("Config", null, configForm , null);
        tabPane.addTab("Lock-Destruction", null, lockKillForm, null);
        tabPane.addTab("Version", null, uhfInfoForm, null);
        tabPane.addTab("Temperature", null, temperatureForm, null);
        tabPane.addTab("Upgrade", null, upgradeForm, null);

        tabPane.setTitleAt(0, "Inventory");
        tabPane.setTitleAt(1, "ReadAndWrite");
        tabPane.setTitleAt(2, "Config");
        tabPane.setTitleAt(3, "Lock-Destruction");
        tabPane.setTitleAt(4, "Version");
        tabPane.setTitleAt(5, "Temperature");
        tabPane.setTitleAt(6, "Upgrade");

        formList.add(inventoryForm);
        formList.add(readWriteForm);
        formList.add(configForm);
        formList.add(lockKillForm);
        formList.add(uhfInfoForm);
        formList.add(temperatureForm);
        formList.add(upgradeForm);
        cmbCommunicationMode.addItem("Serial port");
        cmbCommunicationMode.addItem("Internet port");


        txtIP.setText("192.168.99.200");
        txtPort.setText("9160");

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            for (int i = 1; i <= 50; i++) {
                cmbPortNumber.addItem("COM" + i);
            }
        } else {
            cmbPortNumber.addItem("/dev/ttyS1");
            cmbPortNumber.addItem("/dev/ttyS2");
            cmbPortNumber.addItem("/dev/ttyS3");
            cmbPortNumber.addItem("/dev/ttyUSB0");
            cmbPortNumber.addItem("/dev/ttyUSB1");
            cmbPortNumber.addItem("/dev/ttyUSB2");
            cmbPortNumber.addItem("/dev/ttyUSB3");
        }
    }

    /**
     * 选选项卡的选择项发送改变
     * @param e
     */
    private void tabPaneStateChanged(ChangeEvent e) {
        int index=tabPane.getSelectedIndex();
        for (int i = 0; i < formList.size(); i++) {
            if (i == index) {
                formList.get(i).setVisible(true);
            } else {
                formList.get(i).setVisible(false);
            }
        }
    }
    //连接网络或者串口
    private void btnConnectActionPerformed(ActionEvent e) {
           if(btnConnect.getText().equals("Connect")){
               if(ur4 instanceof RFIDWithUHFSerialPortUR4){
                   //串口
                   String com= (String)cmbPortNumber.getSelectedItem();
                   boolean rsult=ur4SerialPort.init(com);
                   if(!rsult){
                       JOptionPane.showMessageDialog(getContentPane(), "Failed to open the serial port!","", JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                   //连接成功
                   btnConnect.setText("Break off");
                   cmbPortNumber.setEnabled(false);
                   cmbCommunicationMode.setEnabled(false);
               }else{
                   //网口
                   String ip=txtIP.getText();
                   String port=txtPort.getText();
                   if(!StringUtils.isIPAddress(ip)){
                       JOptionPane.showMessageDialog(getContentPane(), "Illegal IP address!","", JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                   if(StringUtils.isEmpty(port)){
                       JOptionPane.showMessageDialog(getContentPane(), "The port cannot be empty!","", JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                   boolean rsult=ur4Network.init(ip,Integer.parseInt(port));
                   if(!rsult){
                       JOptionPane.showMessageDialog(getContentPane(), "Connection failed!","", JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                   //连接成功
                   btnConnect.setText("Break off");
                   txtIP.setEnabled(false);
                   txtPort.setEnabled(false);
               }
           }else{
               if(inventoryForm.isVisible()){
                   inventoryForm.stopInventory();
               }
               ur4.free();
               btnConnect.setText("Connect");
               txtIP.setEnabled(true);
               txtPort.setEnabled(true);
               cmbPortNumber.setEnabled(true);
               cmbCommunicationMode.setEnabled(true);
           }
    }

    private void cmbCommunicationModeActionPerformed(ActionEvent e) {
        if(cmbCommunicationMode.getSelectedIndex()==0){
            System.out.println("serial port");
            panelSerialPort.setVisible(true);
            panelNetWork.setVisible(false);
            if(ur4SerialPort==null){
                ur4SerialPort=new RFIDWithUHFSerialPortUR4();
            }
            ur4=ur4SerialPort;

        }else if(cmbCommunicationMode.getSelectedIndex()==1){
            System.out.println("network");
            panelSerialPort.setVisible(false);
            panelNetWork.setVisible(true);
            if(ur4Network==null){
                ur4Network=new RFIDWithUHFNetworkUR4();
            }
            ur4=ur4Network;
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panel3 = new JPanel();
        panel1 = new JPanel();
        tabPane = new JTabbedPane();
        panel5 = new JPanel();
        panel6 = new JPanel();
        label1 = new JLabel();
        panelSerialPort = new JPanel();
        label3 = new JLabel();
        cmbPortNumber = new JComboBox();
        panelNetWork = new JPanel();
        label2 = new JLabel();
        txtIP = new JTextField();
        label4 = new JLabel();
        txtPort = new JTextField();
        btnConnect = new JButton();
        cmbCommunicationMode = new JComboBox();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setBackground(new Color(238, 238, 238));
                contentPanel.setLayout(null);

                //======== panel3 ========
                {
                    panel3.setLayout(null);

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
                contentPanel.add(panel3);
                panel3.setBounds(10, 240, panel3.getPreferredSize().width, 285);

                //======== panel1 ========
                {
                    panel1.setLayout(null);

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
                contentPanel.add(panel1);
                panel1.setBounds(new Rectangle(new Point(300, 40), panel1.getPreferredSize()));

                //======== tabPane ========
                {
                    tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
                    tabPane.addChangeListener(e -> tabPaneStateChanged(e));
                }
                contentPanel.add(tabPane);
                tabPane.setBounds(5, 80, 1105, 655);

                //======== panel5 ========
                {
                    panel5.setLayout(null);

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
                contentPanel.add(panel5);
                panel5.setBounds(new Rectangle(new Point(25, 90), panel5.getPreferredSize()));

                //======== panel6 ========
                {
                    panel6.setBackground(new Color(138, 154, 249));
                    panel6.setLayout(null);

                    //---- label1 ----
                    label1.setText("Communication mode:");
                    panel6.add(label1);
                    label1.setBounds(new Rectangle(new Point(20, 25), label1.getPreferredSize()));

                    //======== panelSerialPort ========
                    {
                        panelSerialPort.setBackground(new Color(138, 154, 249));
                        panelSerialPort.setLayout(null);

                        //---- label3 ----
                        label3.setText("Serial port number:");
                        panelSerialPort.add(label3);
                        label3.setBounds(5, 10, 131, label3.getPreferredSize().height);
                        panelSerialPort.add(cmbPortNumber);
                        cmbPortNumber.setBounds(130, 5, 105, 30);

                        {
                            // compute preferred size
                            Dimension preferredSize = new Dimension();
                            for(int i = 0; i < panelSerialPort.getComponentCount(); i++) {
                                Rectangle bounds = panelSerialPort.getComponent(i).getBounds();
                                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                            }
                            Insets insets = panelSerialPort.getInsets();
                            preferredSize.width += insets.right;
                            preferredSize.height += insets.bottom;
                            panelSerialPort.setMinimumSize(preferredSize);
                            panelSerialPort.setPreferredSize(preferredSize);
                        }
                    }
                    panel6.add(panelSerialPort);
                    panelSerialPort.setBounds(275, 15, 265, 45);

                    //======== panelNetWork ========
                    {
                        panelNetWork.setBackground(new Color(138, 154, 249));
                        panelNetWork.setLayout(null);

                        //---- label2 ----
                        label2.setText("IP:");
                        panelNetWork.add(label2);
                        label2.setBounds(new Rectangle(new Point(10, 10), label2.getPreferredSize()));
                        panelNetWork.add(txtIP);
                        txtIP.setBounds(35, 5, 150, 30);

                        //---- label4 ----
                        label4.setText("Port:");
                        panelNetWork.add(label4);
                        label4.setBounds(225, 10, 40, 17);
                        panelNetWork.add(txtPort);
                        txtPort.setBounds(260, 5, 85, 30);

                        {
                            // compute preferred size
                            Dimension preferredSize = new Dimension();
                            for(int i = 0; i < panelNetWork.getComponentCount(); i++) {
                                Rectangle bounds = panelNetWork.getComponent(i).getBounds();
                                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                            }
                            Insets insets = panelNetWork.getInsets();
                            preferredSize.width += insets.right;
                            preferredSize.height += insets.bottom;
                            panelNetWork.setMinimumSize(preferredSize);
                            panelNetWork.setPreferredSize(preferredSize);
                        }
                    }
                    panel6.add(panelNetWork);
                    panelNetWork.setBounds(555, 15, 385, 45);

                    //---- btnConnect ----
                    btnConnect.setText("Connect");
                    btnConnect.setBackground(new Color(238, 238, 238));
                    btnConnect.addActionListener(e -> btnConnectActionPerformed(e));
                    panel6.add(btnConnect);
                    btnConnect.setBounds(965, 15, 115, 40);

                    //---- cmbCommunicationMode ----
                    cmbCommunicationMode.addActionListener(e -> cmbCommunicationModeActionPerformed(e));
                    panel6.add(cmbCommunicationMode);
                    cmbCommunicationMode.setBounds(160, 20, 110, cmbCommunicationMode.getPreferredSize().height);

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
                contentPanel.add(panel6);
                panel6.setBounds(-10, -5, 1115, 80);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel panel3;
    private JPanel panel1;
    private JTabbedPane tabPane;
    private JPanel panel5;
    private JPanel panel6;
    private JLabel label1;
    private JPanel panelSerialPort;
    private JLabel label3;
    private JComboBox cmbPortNumber;
    private JPanel panelNetWork;
    private JLabel label2;
    private JTextField txtIP;
    private JLabel label4;
    private JTextField txtPort;
    private JButton btnConnect;
    private JComboBox cmbCommunicationMode;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


}
