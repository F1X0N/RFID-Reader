/*
 * Created by JFormDesigner on Mon Oct 17 17:18:30 CST 2022
 */

package com.uhf.form;

import com.rscja.deviceapi.entity.*;
import com.rscja.deviceapi.interfaces.IUHF;
import com.uhf.UHFMainForm;
import com.uhf.utils.StringUtils;
import jdk.nashorn.internal.scripts.JO;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author zp
 */
public class ConfigForm extends JPanel {
    public ConfigForm() {
        initComponents();
        initUI();
    }

    /**
     * 获取天线功率
     * @param e
     */
    private void btnGetPowerActionPerformed(ActionEvent e) {
        List<AntennaPowerEntity> list=UHFMainForm.ur4.getPowerAll();
        if(list==null){
            JOptionPane.showMessageDialog(this, "Get failed!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for(AntennaPowerEntity entity : list){
            if(entity.getAntennaNameEnum() ==AntennaNameEnum.ANT1){
                int ant1Power= entity.getPower();
                cmbAnt1Power.setSelectedIndex(ant1Power-1);
                System.out.println("ant1Power="+ant1Power);
            }else if(entity.getAntennaNameEnum() ==AntennaNameEnum.ANT2){
                int ant2Power= entity.getPower();
                cmbAnt2Power.setSelectedIndex(ant2Power-1);
                System.out.println("ant2Power="+ant2Power);
            }else if(entity.getAntennaNameEnum() ==AntennaNameEnum.ANT3){
                int ant3Power= entity.getPower();
                cmbAnt3Power.setSelectedIndex(ant3Power-1);
                System.out.println("ant3Power="+ant3Power);
            }else if(entity.getAntennaNameEnum() ==AntennaNameEnum.ANT4){
                int ant4Power= entity.getPower();
                cmbAnt4Power.setSelectedIndex(ant4Power-1);
                System.out.println("ant4Power="+ant4Power);
            }
        }
    }

    /**
     * 设置天线功率
     * @param e
     */
    private void btnSetPoerActionPerformed(ActionEvent e) {
        boolean result= UHFMainForm.ur4.setPower(AntennaNameEnum.ANT1,cmbAnt1Power.getSelectedIndex()+1);
        if(!result){
            JOptionPane.showMessageDialog(this, "Failed to set antenna1!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        result=UHFMainForm.ur4.setPower(AntennaNameEnum.ANT2,cmbAnt2Power.getSelectedIndex()+1);
        if(!result){
            JOptionPane.showMessageDialog(this, "Failed to set antenna2!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        result= UHFMainForm.ur4.setPower(AntennaNameEnum.ANT3,cmbAnt3Power.getSelectedIndex()+1);
        if(!result){
            JOptionPane.showMessageDialog(this, "Failed to set antenna3!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        result=  UHFMainForm.ur4.setPower(AntennaNameEnum.ANT4,cmbAnt4Power.getSelectedIndex()+1);
        if(!result){
            JOptionPane.showMessageDialog(this, "Failed to set antenna4!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Setting antenna succeeded!","", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 获取天线
     * @param e
     */
    private void btnGetANTActionPerformed(ActionEvent e) {
        List<AntennaState>  list =UHFMainForm.ur4.getAntenna();
        if(list==null){
            JOptionPane.showMessageDialog(this, "Get failed!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        cbANT1.setSelected(false);
        cbANT2.setSelected(false);
        cbANT3.setSelected(false);
        cbANT4.setSelected(false);
        for (AntennaState ant:list){
            if(ant.getAntennaName()==AntennaNameEnum.ANT1 && ant.isEnable()){
                cbANT1.setSelected(true);
            }else if(ant.getAntennaName()==AntennaNameEnum.ANT2 && ant.isEnable()){
                cbANT2.setSelected(true);
            }else if(ant.getAntennaName()==AntennaNameEnum.ANT3 && ant.isEnable()){
                cbANT3.setSelected(true);
            }else if(ant.getAntennaName()==AntennaNameEnum.ANT4 && ant.isEnable()){
                cbANT4.setSelected(true);
            }
        }
    }
    /**
     * 设置天线
     * @param e
     */
    private void btnSetANTActionPerformed(ActionEvent e) {
        List<AntennaState> list=new ArrayList<>();
        list.add(new AntennaState(AntennaNameEnum.ANT1,cbANT1.isSelected()));
        list.add(new AntennaState(AntennaNameEnum.ANT2,cbANT2.isSelected()));
        list.add(new AntennaState(AntennaNameEnum.ANT3,cbANT3.isSelected()));
        list.add(new AntennaState(AntennaNameEnum.ANT4,cbANT4.isSelected()));
        boolean result=UHFMainForm.ur4.setAntenna(list);
        if(!result){
            JOptionPane.showMessageDialog(this, "Setting failed!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Setting succeeded!","", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    /**
     * 获取频段
     * @param e
     */
    private void btnGetFrequencyBandActionPerformed(ActionEvent e) {
        int region=UHFMainForm.ur4.getFrequencyMode();
        switch (region) {
            case 0x01:
                cbFrequencyBand.setSelectedIndex(0);
                break;
            case 0x02:
                cbFrequencyBand.setSelectedIndex(1);
                break;
            case 0x04:
                cbFrequencyBand.setSelectedIndex(2);
                break;
            case 0x08:
                cbFrequencyBand.setSelectedIndex(3);
                break;
            case 0x16:
                cbFrequencyBand.setSelectedIndex(4);
                break;
            case 0x32:
                cbFrequencyBand.setSelectedIndex(5);
                break;
            case 0x34:
                cbFrequencyBand.setSelectedIndex(6);
                break;
            case 0x33:
                cbFrequencyBand.setSelectedIndex(7);
                break;
            case 0x36:
                cbFrequencyBand.setSelectedIndex(8);
                break;
            case 0x37:
                cbFrequencyBand.setSelectedIndex(9);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Get failed!","", JOptionPane.ERROR_MESSAGE);
                break;

        }
    }

    /**
     * 设置频段
     * @param e
     */
    private void btnSetFrequencyBandActionPerformed(ActionEvent e) {

        int region=-1;
        switch (cbFrequencyBand.getSelectedIndex())
        {
            case 0:
                region = 0x01;
                break;
            case 1:
                region = 0x02;
                break;
            case 2:
                region = 0x04;
                break;
            case 3:
                region = 0x08;
                break;
            case 4:
                region = 0x16;
                break;
            case 5:
                region = 0x32;
                break;
            case 6:
                region = 0x34;
                break;
            case 7:
                region = 0x33;
                break;
            case 8:
                region = 0x36;
                break;
            case 9:
                region = 0x37;
                break;

        }
        if(region==-1){
            JOptionPane.showMessageDialog(this, "Setting failed!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean result=UHFMainForm.ur4.setFrequencyMode( (byte) region);
        if(result){
            JOptionPane.showMessageDialog(this, "Setting succeeded!","", JOptionPane.INFORMATION_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(this, "Setting failed!","", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 设置协议
     * @param e
     */
    private void btnSetProtocolActionPerformed(ActionEvent e) {
        int p=cmbProtocol.getSelectedIndex();
        if(p<=0){
            JOptionPane.showMessageDialog(this, "Setting failed!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean result= UHFMainForm.ur4.setProtocol(p);
        if(!result){
            JOptionPane.showMessageDialog(this, "Setting failed!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Setting succeeded!","", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * 获取协议
     * @param e
     */
    private void btnGetProtocolActionPerformed(ActionEvent e) {
        int result= UHFMainForm.ur4.getProtocol();
        if(result==-1){
            JOptionPane.showMessageDialog(this, "Get failed!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        cmbProtocol.setSelectedIndex(result);
        JOptionPane.showMessageDialog(this, "Get succeeded!","", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * 获取链路
     * @param e
     */
    private void btnGetLinkActionPerformed(ActionEvent e) {
        int result = UHFMainForm.ur4.getRFLink();
        if(result==-1){
            JOptionPane.showMessageDialog(this, "Get failed!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        cmbRFLink.setSelectedIndex(result);
        JOptionPane.showMessageDialog(this, "Get succeeded!","", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * 设置链路
     * @param e
     */
    private void btnSetLinkActionPerformed(ActionEvent e) {
        int l =cmbRFLink.getSelectedIndex();
        if(l<0){
            JOptionPane.showMessageDialog(this, "Setting failed!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean result= UHFMainForm.ur4.setRFLink(l);
        if(!result){
            JOptionPane.showMessageDialog(this, "Setting failed!","", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Setting succeeded!","", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * 获取工作模式
     * @param e
     */
    private void btnGetFrequencyModeActionPerformed(ActionEvent e) {
        int result = UHFMainForm.ur4.getWorkMode();
        if(result < 0){
            JOptionPane.showMessageDialog(this,"Get failed！","",JOptionPane.ERROR_MESSAGE);
            return;
        }
        cmbWorkMode.setSelectedIndex(result);
        JOptionPane.showMessageDialog(this,"Get succeeded！","",JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * 设置工作模式
     * @param e
     */
    private void btnSetFrequencyModeActionPerformed(ActionEvent e) {
        int w = cmbWorkMode.getSelectedIndex();
        if(w<0){
            JOptionPane.showMessageDialog(this,"Setting failed","",JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean result = UHFMainForm.ur4.setWorkMode(w);
        if(!result){
            JOptionPane.showMessageDialog(this,"Setting failed","",JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this,"Setting succeeded","",JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * 获取盘点模式
     * @param e
     */
    private void btnGetEPCAndTIDUserModeActionPerformed(ActionEvent e) {
        InventoryModeEntity result = UHFMainForm.ur4.getEPCAndTIDUserMode();
        int User_prt = result.getUserOffset();
        int User_len = result.getUserLength();
        if(result == null){
            JOptionPane.showMessageDialog(this,"Get failed","",JOptionPane.ERROR_MESSAGE);
            return;
        }
        cmbEPCAndTIDUserMode.setSelectedIndex(result.getMode());
        if(result.getMode()==2) {
            tf_user_prt.setText(String.valueOf(User_prt));
            tf_user_len.setText(String.valueOf(User_len));
        }
        JOptionPane.showMessageDialog(this,"Get succeeded","",JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * 设置盘点模式
     * @param e
     */
    private void btnSetEPCAndTIDUserModeActionPerformed(ActionEvent e) {
        if (cmbEPCAndTIDUserMode.getSelectedIndex()==0){
            boolean result1 = UHFMainForm.ur4.setEPCMode();
            if(!result1){
                JOptionPane.showMessageDialog(this,"Setting failed","",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                JOptionPane.showMessageDialog(this,"Setting succeeded","",JOptionPane.INFORMATION_MESSAGE);
            }
        }else if (cmbEPCAndTIDUserMode.getSelectedIndex()==1){
            boolean result2 = UHFMainForm.ur4.setEPCAndTIDMode();
            if(!result2){
                JOptionPane.showMessageDialog(this,"Setting failed","",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                JOptionPane.showMessageDialog(this,"Setting succeeded","",JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(cmbEPCAndTIDUserMode.getSelectedIndex()==2) {
            String User_Prt = tf_user_prt.getText();
            String User_Len = tf_user_len.getText();


            if (StringUtils.isEmpty(User_Prt)){
                JOptionPane.showMessageDialog(this,"The starting address cannot be empty","",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (StringUtils.isEmpty(User_Len)){
                JOptionPane.showMessageDialog(this,"Length cannot be empty","",JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean result3 = UHFMainForm.ur4.setEPCAndTIDUserMode(Integer.parseInt(User_Prt),Integer.parseInt(User_Len));
            if(result3){
                JOptionPane.showMessageDialog(this, "Setting succeeded!","", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Setting failed!","", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     *
     * 获取蜂鸣器
     * @param e
     */
    private void btnGetBeepActionPerformed(ActionEvent e) {
        char[] result = UHFMainForm.ur4.getBeep();
        if (result == null) {
            JOptionPane.showMessageDialog(this, "Get failed", "", JOptionPane.ERROR_MESSAGE);
            return;
        }else {
            JOptionPane.showMessageDialog(this, "Get succeeded", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     *
     * 设置蜂鸣器
     * @param e
     */
    private void btnSetBeepActionPerformed(ActionEvent e) {
        int mode = -1;
        if(rbtn_Beep_On.isSelected()){
            mode = 1;
        }else if(rbtn_Beep_Off.isSelected()){
            mode = 0;
        }
       boolean result = UHFMainForm.ur4.setBeep(mode);
        if(!result){
            JOptionPane.showMessageDialog(this,"Setting failed","",JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this,"Setting succeeded","",JOptionPane.INFORMATION_MESSAGE);

    }
    /**
     *蜂鸣器 开
     * @param e
     */
    private void rbtn_Beep_OnActionPerformed(ActionEvent e) {
        rbtn_Beep_On.setSelected(true);
        rbtn_Beep_Off.setSelected(false);
    }
    /**
     *蜂鸣器 关
     * @param e
     */
    private void rbtn_Beep_OffActionPerformed(ActionEvent e) {
        rbtn_Beep_On.setSelected(false);
        rbtn_Beep_Off.setSelected(true);
    }
    /**
     *获取Gen2
     * @param e
     */
    private void btnGetGen2ActionPerformed(ActionEvent e) {
        Gen2Entity getGen2 = UHFMainForm.ur4.getGen2();
        if (getGen2 == null) {
            JOptionPane.showMessageDialog(this, "Get failed", "", JOptionPane.ERROR_MESSAGE);
            return;
        }
            int session = getGen2.getQuerySession();
            int target = getGen2.getQueryTarget();
            cmb_Gen2_session.setSelectedIndex(session);
            cmb_Gen2_target.setSelectedIndex(target);
            JOptionPane.showMessageDialog(this, "Get succeeded", "", JOptionPane.INFORMATION_MESSAGE);
            }
    /**
     *设置Gen2
     * @param e
     */
        private void btnSetGen2ActionPerformed(ActionEvent e) {
            Gen2Entity entity = UHFMainForm.ur4.getGen2();
            int session = cmb_Gen2_session.getSelectedIndex();
            int target = cmb_Gen2_target.getSelectedIndex();
            entity.setQuerySession(session);
            entity.setQueryTarget(target);

            boolean result = UHFMainForm.ur4.setGen2(entity);
            if (!result){
                JOptionPane.showMessageDialog(this,"Setting failed","",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                JOptionPane.showMessageDialog(this,"Setting succeeded","",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    /**
     *获取本地IP
     * @param e
     */
        private void btnGetLocal_IPActionPerformed(ActionEvent e) {
            ReaderIPEntity result = UHFMainForm.ur4.getIPAndPort();
            String ip = result.getIp();
            String SubNetMask = result.getSubnetMask();
            String GateWay = result.getGateway();
            int Port = result.getPort();

            textFieldIP.setText(ip);
            textFieldSubNetMask.setText(SubNetMask);
            textFieldGateWay.setText(GateWay);
            textFieldPort.setText(String.valueOf(Port));

            if(result == null){
                JOptionPane.showMessageDialog(this,"Get failed","",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                JOptionPane.showMessageDialog(this,"Get succeeded","",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    /**
     *设置本地IP
     * @param e
     */
        private void btnSetLocal_IPActionPerformed(ActionEvent e) {
            String Ip = textFieldIP.getText();
            String SubNetMask = textFieldSubNetMask.getText();
            String GateWay = textFieldGateWay.getText();
            String Port = textFieldPort.getText();

            if(StringUtils.isEmpty(Ip)){
                JOptionPane.showMessageDialog(this,"IP address cannot be empty","",JOptionPane.ERROR_MESSAGE);
                return;
            }else if (StringUtils.isEmpty(SubNetMask)){
                JOptionPane.showMessageDialog(this,"Subnet mask cannot be empty","",JOptionPane.ERROR_MESSAGE);
                return;
            }else if (StringUtils.isEmpty(GateWay)){
                JOptionPane.showMessageDialog(this,"Gateway cannot be empty","",JOptionPane.ERROR_MESSAGE);
                return;
            }else if (StringUtils.isEmpty(Port)){
                JOptionPane.showMessageDialog(this,"The port cannot be empty","",JOptionPane.ERROR_MESSAGE);
                return;
            }

            ReaderIPEntity uhfIpConfig = new ReaderIPEntity();
            uhfIpConfig.setIp(Ip);
            uhfIpConfig.setSubnetMask(SubNetMask);
            uhfIpConfig.setGateway(GateWay);
            uhfIpConfig.setPort(Integer.parseInt(Port));

            boolean result = UHFMainForm.ur4.setIPAndPort(uhfIpConfig);
            if(!result){
                JOptionPane.showMessageDialog(this,"Setting failed","",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                JOptionPane.showMessageDialog(this,"Setting succeeded","",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    /**
     *获取目标IP
     * @param e
     */
        private void btnGetDestinationIPActionPerformed(ActionEvent e) {
            ReaderIPEntity result = UHFMainForm.ur4.getDestinationIPAndPort();
            String DIp = result.getIp();
            int DPort = result.getPort();

            textFieldDestinationIP.setText(DIp);
            textFieldDestinationPort.setText(String.valueOf(DPort));

            if(result == null){
                JOptionPane.showMessageDialog(this,"Get failed","",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                JOptionPane.showMessageDialog(this,"Get succeeded","",JOptionPane.INFORMATION_MESSAGE);
            }

        }
    /**
     *设置目标IP
     * @param e
     */
        private void btnSetDestinationPortActionPerformed(ActionEvent e) {
            String DIp = textFieldDestinationIP.getText();
            String DPort = textFieldDestinationPort.getText();
            if(StringUtils.isEmpty(DIp)){
                JOptionPane.showMessageDialog(this,"The destination IP address cannot be empty","",JOptionPane.ERROR_MESSAGE);
                return;
            }else if (StringUtils.isEmpty(DPort)){
                JOptionPane.showMessageDialog(this,"The target port cannot be empty","",JOptionPane.ERROR_MESSAGE);
                return;
            }
            ReaderIPEntity uhfIpConfig = new ReaderIPEntity();
            uhfIpConfig.setIp(DIp);
            uhfIpConfig.setPort(Integer.parseInt(DPort));

            boolean result = UHFMainForm.ur4.setDestinationIPAndPort(uhfIpConfig);
            if(!result){
                JOptionPane.showMessageDialog(this,"Setting failed","",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                JOptionPane.showMessageDialog(this,"Setting succeeded","",JOptionPane.INFORMATION_MESSAGE);
            }

        }
    /**
     *设置GPIO
     * @param e
     */
        private void btnSetGPIOActionPerformed(ActionEvent e) {
            byte relay = (byte) cmbRelay.getSelectedIndex();
            byte input1 = (byte) cmbInput1.getSelectedIndex();
            byte input2 = (byte) cmbInput2.getSelectedIndex();
            if(relay < 0 || input1<0 || input2<0){
                JOptionPane.showMessageDialog(this,"Setting failed","",JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean result = UHFMainForm.ur4.setGPO(input1,input2,relay);
            if(!result){
                JOptionPane.showMessageDialog(this,"Setting failed","",JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this,"Setting succeeded","",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    /**
     *获取GPIO
     * @param e
     */
        private void btnGetGPIOActionPerformed(ActionEvent e) {
            List<GPIStateEntity> list =  UHFMainForm.ur4.getGPI();
            if(list == null){
                JOptionPane.showMessageDialog(this,"Get failed","",JOptionPane.ERROR_MESSAGE);
                return;
            }
            for(GPIStateEntity entity :list){
                switch (entity.getGPIName()){
                    case GPIStateEntity.GPI1:
                        cmbInput1.setSelectedIndex(entity.getGPIState());
                        break;
                    case GPIStateEntity.GPI2:
                        cmbInput2.setSelectedIndex(entity.getGPIState());
                        break;
                }
            }
            JOptionPane.showMessageDialog(this,"Get succeeded","",JOptionPane.INFORMATION_MESSAGE);

        }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        cmbAnt1Power = new JComboBox();
        cmbAnt2Power = new JComboBox();
        label2 = new JLabel();
        cmbAnt3Power = new JComboBox();
        label3 = new JLabel();
        cmbAnt4Power = new JComboBox();
        label4 = new JLabel();
        btnGetPower = new JButton();
        btnSetPoer = new JButton();
        panel2 = new JPanel();
        cbANT1 = new JCheckBox();
        cbANT2 = new JCheckBox();
        cbANT3 = new JCheckBox();
        cbANT4 = new JCheckBox();
        btnSetANT = new JButton();
        btnGetANT = new JButton();
        panel3 = new JPanel();
        label5 = new JLabel();
        cbFrequencyBand = new JComboBox();
        btnGetFrequencyBand = new JButton();
        btnSetFrequencyBand = new JButton();
        panel5 = new JPanel();
        label6 = new JLabel();
        cmbProtocol = new JComboBox();
        btnSetProtocol = new JButton();
        btnGetProtocol = new JButton();
        panel17 = new JPanel();
        label22 = new JLabel();
        cmbEPCAndTIDUserMode = new JComboBox();
        btnGetEPCAndTIDUserMode = new JButton();
        btnSetEPCAndTIDUserMode = new JButton();
        label23 = new JLabel();
        tf_user_prt = new JTextField();
        label24 = new JLabel();
        tf_user_len = new JTextField();
        panel18 = new JPanel();
        label25 = new JLabel();
        cmb_Gen2_session = new JComboBox();
        label26 = new JLabel();
        cmb_Gen2_target = new JComboBox();
        btnSetGen2 = new JButton();
        btnGetGen2 = new JButton();
        panel19 = new JPanel();
        label27 = new JLabel();
        rbtn_Beep_On = new JRadioButton();
        rbtn_Beep_Off = new JRadioButton();
        btnGetBeep = new JButton();
        btnSetBeep = new JButton();
        panel20 = new JPanel();
        label28 = new JLabel();
        textFieldIP = new JTextField();
        label29 = new JLabel();
        label30 = new JLabel();
        textFieldSubNetMask = new JTextField();
        textFieldGateWay = new JTextField();
        btnGetLocal_IP = new JButton();
        btnSetLocal_IP = new JButton();
        label33 = new JLabel();
        textFieldPort = new JTextField();
        panel21 = new JPanel();
        label31 = new JLabel();
        textFieldDestinationIP = new JTextField();
        label32 = new JLabel();
        textFieldDestinationPort = new JTextField();
        btnSetDestinationPort = new JButton();
        btnGetDestinationIP = new JButton();
        panel22 = new JPanel();
        label34 = new JLabel();
        cmbWorkMode = new JComboBox();
        btnSetWorkMode = new JButton();
        btnGetWorkMode = new JButton();
        panel23 = new JPanel();
        cmbRFLink = new JComboBox();
        label7 = new JLabel();
        btnSetRFLink = new JButton();
        btnGetRFLink = new JButton();
        panel24 = new JPanel();
        btnSetGPIO = new JButton();
        cmbRelay = new JComboBox();
        label35 = new JLabel();
        label36 = new JLabel();
        label37 = new JLabel();
        btnGetGPIO = new JButton();
        cmbInput1 = new JComboBox();
        cmbInput2 = new JComboBox();

        //======== this ========
        setLayout(null);

        //======== panel1 ========
        {
            panel1.setPreferredSize(new Dimension(42, 42));
            panel1.setBorder(new TitledBorder("Power"));
            panel1.setLayout(null);

            //---- label1 ----
            label1.setText("ANT1:");
            panel1.add(label1);
            label1.setBounds(15, 25, 40, label1.getPreferredSize().height);
            panel1.add(cmbAnt1Power);
            cmbAnt1Power.setBounds(55, 20, 80, cmbAnt1Power.getPreferredSize().height);
            panel1.add(cmbAnt2Power);
            cmbAnt2Power.setBounds(190, 20, 80, 30);

            //---- label2 ----
            label2.setText("ANT2:");
            panel1.add(label2);
            label2.setBounds(150, 25, 40, 17);
            panel1.add(cmbAnt3Power);
            cmbAnt3Power.setBounds(55, 55, 75, 30);

            //---- label3 ----
            label3.setText("ANT3:");
            panel1.add(label3);
            label3.setBounds(15, 60, 40, 17);
            panel1.add(cmbAnt4Power);
            cmbAnt4Power.setBounds(190, 55, 80, 30);

            //---- label4 ----
            label4.setText("ANT4:");
            panel1.add(label4);
            label4.setBounds(150, 60, 40, 17);

            //---- btnGetPower ----
            btnGetPower.setText("GET POWER");
            btnGetPower.addActionListener(e -> btnGetPowerActionPerformed(e));
            panel1.add(btnGetPower);
            btnGetPower.setBounds(40, 95, 105, 30);

            //---- btnSetPoer ----
            btnSetPoer.setText("SET POWER");
            btnSetPoer.addActionListener(e -> btnSetPoerActionPerformed(e));
            panel1.add(btnSetPoer);
            btnSetPoer.setBounds(165, 95, 110, 30);

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
        panel1.setBounds(15, 10, 325, 140);

        //======== panel2 ========
        {
            panel2.setPreferredSize(new Dimension(42, 42));
            panel2.setBorder(new TitledBorder("Antenna"));
            panel2.setLayout(null);

            //---- cbANT1 ----
            cbANT1.setText("ANT1");
            panel2.add(cbANT1);
            cbANT1.setBounds(new Rectangle(new Point(15, 25), cbANT1.getPreferredSize()));

            //---- cbANT2 ----
            cbANT2.setText("ANT2");
            panel2.add(cbANT2);
            cbANT2.setBounds(85, 25, 55, 22);

            //---- cbANT3 ----
            cbANT3.setText("ANT3");
            panel2.add(cbANT3);
            cbANT3.setBounds(155, 25, 55, 22);

            //---- cbANT4 ----
            cbANT4.setText("ANT4");
            panel2.add(cbANT4);
            cbANT4.setBounds(225, 25, 55, 22);

            //---- btnSetANT ----
            btnSetANT.setText("SET ANTENNA");
            btnSetANT.addActionListener(e -> btnSetANTActionPerformed(e));
            panel2.add(btnSetANT);
            btnSetANT.setBounds(175, 55, 120, 30);

            //---- btnGetANT ----
            btnGetANT.setText("GET ANTENNA");
            btnGetANT.addActionListener(e -> btnGetANTActionPerformed(e));
            panel2.add(btnGetANT);
            btnGetANT.setBounds(45, 55, 120, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel2.getComponentCount(); i++) {
                    Rectangle bounds = panel2.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel2.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel2.setMinimumSize(preferredSize);
                panel2.setPreferredSize(preferredSize);
            }
        }
        add(panel2);
        panel2.setBounds(15, 155, 325, 95);

        //======== panel3 ========
        {
            panel3.setPreferredSize(new Dimension(42, 42));
            panel3.setBorder(new TitledBorder("Frequency band"));
            panel3.setLayout(null);

            //---- label5 ----
            label5.setText("Frequency band:");
            panel3.add(label5);
            label5.setBounds(10, 35, 105, 17);
            panel3.add(cbFrequencyBand);
            cbFrequencyBand.setBounds(125, 30, 190, 30);

            //---- btnGetFrequencyBand ----
            btnGetFrequencyBand.setText("READ FREQUENCY");
            btnGetFrequencyBand.addActionListener(e -> btnGetFrequencyBandActionPerformed(e));
            panel3.add(btnGetFrequencyBand);
            btnGetFrequencyBand.setBounds(20, 65, 140, 30);

            //---- btnSetFrequencyBand ----
            btnSetFrequencyBand.setText("SET FREQUENCY");
            btnSetFrequencyBand.addActionListener(e -> btnSetFrequencyBandActionPerformed(e));
            panel3.add(btnSetFrequencyBand);
            btnSetFrequencyBand.setBounds(170, 65, 135, 30);

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
        panel3.setBounds(15, 260, 325, 110);

        //======== panel5 ========
        {
            panel5.setPreferredSize(new Dimension(42, 42));
            panel5.setBorder(new TitledBorder("Procotol"));
            panel5.setLayout(null);

            //---- label6 ----
            label6.setText("Protocol:");
            panel5.add(label6);
            label6.setBounds(20, 35, 70, 17);
            panel5.add(cmbProtocol);
            cmbProtocol.setBounds(100, 30, 180, 30);

            //---- btnSetProtocol ----
            btnSetProtocol.setText("SET PROTOCOL");
            btnSetProtocol.addActionListener(e -> btnSetProtocolActionPerformed(e));
            panel5.add(btnSetProtocol);
            btnSetProtocol.setBounds(160, 65, 135, 30);

            //---- btnGetProtocol ----
            btnGetProtocol.setText("GET PROTOCOL");
            btnGetProtocol.addActionListener(e -> btnGetProtocolActionPerformed(e));
            panel5.add(btnGetProtocol);
            btnGetProtocol.setBounds(20, 65, 125, 30);

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
        add(panel5);
        panel5.setBounds(15, 375, 325, 105);

        //======== panel17 ========
        {
            panel17.setPreferredSize(new Dimension(42, 42));
            panel17.setBorder(new TitledBorder("InventoryMode"));
            panel17.setLayout(null);

            //---- label22 ----
            label22.setText("Mode:");
            panel17.add(label22);
            label22.setBounds(20, 30, 40, 17);
            panel17.add(cmbEPCAndTIDUserMode);
            cmbEPCAndTIDUserMode.setBounds(55, 25, 235, 30);

            //---- btnGetEPCAndTIDUserMode ----
            btnGetEPCAndTIDUserMode.setText("GET MODE");
            btnGetEPCAndTIDUserMode.addActionListener(e -> btnGetEPCAndTIDUserModeActionPerformed(e));
            panel17.add(btnGetEPCAndTIDUserMode);
            btnGetEPCAndTIDUserMode.setBounds(55, 95, 95, 30);

            //---- btnSetEPCAndTIDUserMode ----
            btnSetEPCAndTIDUserMode.setText("SET MODE");
            btnSetEPCAndTIDUserMode.addActionListener(e -> btnSetEPCAndTIDUserModeActionPerformed(e));
            panel17.add(btnSetEPCAndTIDUserMode);
            btnSetEPCAndTIDUserMode.setBounds(175, 95, 95, 30);

            //---- label23 ----
            label23.setText("User Ptr:");
            panel17.add(label23);
            label23.setBounds(new Rectangle(new Point(25, 65), label23.getPreferredSize()));
            panel17.add(tf_user_prt);
            tf_user_prt.setBounds(105, 60, 60, tf_user_prt.getPreferredSize().height);

            //---- label24 ----
            label24.setText("User Len:");
            panel17.add(label24);
            label24.setBounds(165, 65, 78, 17);
            panel17.add(tf_user_len);
            tf_user_len.setBounds(225, 60, 65, tf_user_len.getPreferredSize().height);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel17.getComponentCount(); i++) {
                    Rectangle bounds = panel17.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel17.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel17.setMinimumSize(preferredSize);
                panel17.setPreferredSize(preferredSize);
            }
        }
        add(panel17);
        panel17.setBounds(350, 10, 325, 140);

        //======== panel18 ========
        {
            panel18.setPreferredSize(new Dimension(42, 42));
            panel18.setBorder(new TitledBorder("Gen2"));
            panel18.setLayout(null);

            //---- label25 ----
            label25.setText("Session:");
            panel18.add(label25);
            label25.setBounds(10, 30, 75, 17);
            panel18.add(cmb_Gen2_session);
            cmb_Gen2_session.setBounds(70, 25, 85, 30);

            //---- label26 ----
            label26.setText("Target");
            panel18.add(label26);
            label26.setBounds(165, 30, 75, 17);
            panel18.add(cmb_Gen2_target);
            cmb_Gen2_target.setBounds(215, 25, 85, 30);

            //---- btnSetGen2 ----
            btnSetGen2.setText("SET GEN2");
            btnSetGen2.addActionListener(e -> btnSetGen2ActionPerformed(e));
            panel18.add(btnSetGen2);
            btnSetGen2.setBounds(175, 70, 95, 30);

            //---- btnGetGen2 ----
            btnGetGen2.setText("GET GEN2");
            btnGetGen2.addActionListener(e -> btnGetGen2ActionPerformed(e));
            panel18.add(btnGetGen2);
            btnGetGen2.setBounds(45, 70, 95, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel18.getComponentCount(); i++) {
                    Rectangle bounds = panel18.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel18.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel18.setMinimumSize(preferredSize);
                panel18.setPreferredSize(preferredSize);
            }
        }
        add(panel18);
        panel18.setBounds(350, 160, 325, 115);

        //======== panel19 ========
        {
            panel19.setPreferredSize(new Dimension(42, 42));
            panel19.setBorder(new TitledBorder("Buzzer"));
            panel19.setLayout(null);

            //---- label27 ----
            label27.setText("Buzzer:");
            panel19.add(label27);
            label27.setBounds(15, 35, 55, 17);

            //---- rbtn_Beep_On ----
            rbtn_Beep_On.setText("ON");
            rbtn_Beep_On.addActionListener(e -> rbtn_Beep_OnActionPerformed(e));
            panel19.add(rbtn_Beep_On);
            rbtn_Beep_On.setBounds(75, 35, 50, rbtn_Beep_On.getPreferredSize().height);

            //---- rbtn_Beep_Off ----
            rbtn_Beep_Off.setText("OFF");
            rbtn_Beep_Off.addActionListener(e -> rbtn_Beep_OffActionPerformed(e));
            panel19.add(rbtn_Beep_Off);
            rbtn_Beep_Off.setBounds(140, 35, 55, 21);

            //---- btnGetBeep ----
            btnGetBeep.setText("GET BUZZER");
            btnGetBeep.addActionListener(e -> btnGetBeepActionPerformed(e));
            panel19.add(btnGetBeep);
            btnGetBeep.setBounds(40, 85, 105, 30);

            //---- btnSetBeep ----
            btnSetBeep.setText("SET BUZZER");
            btnSetBeep.addActionListener(e -> btnSetBeepActionPerformed(e));
            panel19.add(btnSetBeep);
            btnSetBeep.setBounds(180, 85, 100, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel19.getComponentCount(); i++) {
                    Rectangle bounds = panel19.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel19.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel19.setMinimumSize(preferredSize);
                panel19.setPreferredSize(preferredSize);
            }
        }
        add(panel19);
        panel19.setBounds(350, 290, 325, 140);

        //======== panel20 ========
        {
            panel20.setPreferredSize(new Dimension(42, 42));
            panel20.setBorder(new TitledBorder("Local IP"));
            panel20.setLayout(null);

            //---- label28 ----
            label28.setText("IP:");
            panel20.add(label28);
            label28.setBounds(new Rectangle(new Point(30, 20), label28.getPreferredSize()));
            panel20.add(textFieldIP);
            textFieldIP.setBounds(90, 15, 245, textFieldIP.getPreferredSize().height);

            //---- label29 ----
            label29.setText("SubNetMask:");
            panel20.add(label29);
            label29.setBounds(5, 50, 85, label29.getPreferredSize().height);

            //---- label30 ----
            label30.setText("GateWay:");
            panel20.add(label30);
            label30.setBounds(15, 80, 60, label30.getPreferredSize().height);
            panel20.add(textFieldSubNetMask);
            textFieldSubNetMask.setBounds(90, 45, 245, textFieldSubNetMask.getPreferredSize().height);
            panel20.add(textFieldGateWay);
            textFieldGateWay.setBounds(90, 75, 245, textFieldGateWay.getPreferredSize().height);

            //---- btnGetLocal_IP ----
            btnGetLocal_IP.setText("GET IP");
            btnGetLocal_IP.addActionListener(e -> btnGetLocal_IPActionPerformed(e));
            panel20.add(btnGetLocal_IP);
            btnGetLocal_IP.setBounds(85, 145, 85, 30);

            //---- btnSetLocal_IP ----
            btnSetLocal_IP.setText("SET IP");
            btnSetLocal_IP.addActionListener(e -> btnSetLocal_IPActionPerformed(e));
            panel20.add(btnSetLocal_IP);
            btnSetLocal_IP.setBounds(205, 145, 80, 30);

            //---- label33 ----
            label33.setText("Port:");
            panel20.add(label33);
            label33.setBounds(30, 115, 45, 17);
            panel20.add(textFieldPort);
            textFieldPort.setBounds(90, 110, 245, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel20.getComponentCount(); i++) {
                    Rectangle bounds = panel20.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel20.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel20.setMinimumSize(preferredSize);
                panel20.setPreferredSize(preferredSize);
            }
        }
        add(panel20);
        panel20.setBounds(710, 15, 380, 190);

        //======== panel21 ========
        {
            panel21.setPreferredSize(new Dimension(42, 42));
            panel21.setBorder(new TitledBorder("DestinationIP"));
            panel21.setLayout(null);

            //---- label31 ----
            label31.setText("IP:");
            panel21.add(label31);
            label31.setBounds(40, 30, 30, 17);
            panel21.add(textFieldDestinationIP);
            textFieldDestinationIP.setBounds(100, 20, 245, 30);

            //---- label32 ----
            label32.setText("Port:");
            panel21.add(label32);
            label32.setBounds(40, 60, 45, 17);
            panel21.add(textFieldDestinationPort);
            textFieldDestinationPort.setBounds(100, 55, 245, 30);

            //---- btnSetDestinationPort ----
            btnSetDestinationPort.setText("SET IP");
            btnSetDestinationPort.addActionListener(e -> btnSetDestinationPortActionPerformed(e));
            panel21.add(btnSetDestinationPort);
            btnSetDestinationPort.setBounds(215, 95, 80, 30);

            //---- btnGetDestinationIP ----
            btnGetDestinationIP.setText("GET IP");
            btnGetDestinationIP.addActionListener(e -> btnGetDestinationIPActionPerformed(e));
            panel21.add(btnGetDestinationIP);
            btnGetDestinationIP.setBounds(95, 95, 85, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel21.getComponentCount(); i++) {
                    Rectangle bounds = panel21.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel21.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel21.setMinimumSize(preferredSize);
                panel21.setPreferredSize(preferredSize);
            }
        }
        add(panel21);
        panel21.setBounds(710, 210, 380, 140);

        //======== panel22 ========
        {
            panel22.setPreferredSize(new Dimension(42, 42));
            panel22.setBorder(new TitledBorder("Working Mode"));
            panel22.setLayout(null);

            //---- label34 ----
            label34.setText("Mode:");
            panel22.add(label34);
            label34.setBounds(10, 25, 75, 17);
            panel22.add(cmbWorkMode);
            cmbWorkMode.setBounds(70, 20, 235, 30);

            //---- btnSetWorkMode ----
            btnSetWorkMode.setText("SET MODE");
            btnSetWorkMode.addActionListener(e -> btnSetFrequencyModeActionPerformed(e));
            panel22.add(btnSetWorkMode);
            btnSetWorkMode.setBounds(175, 100, 100, 30);

            //---- btnGetWorkMode ----
            btnGetWorkMode.setText("GET MODE");
            btnGetWorkMode.addActionListener(e -> btnGetFrequencyModeActionPerformed(e));
            panel22.add(btnGetWorkMode);
            btnGetWorkMode.setBounds(50, 100, 100, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel22.getComponentCount(); i++) {
                    Rectangle bounds = panel22.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel22.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel22.setMinimumSize(preferredSize);
                panel22.setPreferredSize(preferredSize);
            }
        }
        add(panel22);
        panel22.setBounds(350, 435, 325, 160);

        //======== panel23 ========
        {
            panel23.setPreferredSize(new Dimension(42, 42));
            panel23.setBorder(new TitledBorder("Link"));
            panel23.setLayout(null);
            panel23.add(cmbRFLink);
            cmbRFLink.setBounds(55, 30, 220, 30);

            //---- label7 ----
            label7.setText("Link:");
            panel23.add(label7);
            label7.setBounds(15, 35, 40, 17);

            //---- btnSetRFLink ----
            btnSetRFLink.setText("SET LINK");
            btnSetRFLink.addActionListener(e -> btnSetLinkActionPerformed(e));
            panel23.add(btnSetRFLink);
            btnSetRFLink.setBounds(155, 65, 95, 30);

            //---- btnGetRFLink ----
            btnGetRFLink.setText("GET LINK");
            btnGetRFLink.addActionListener(e -> btnGetLinkActionPerformed(e));
            panel23.add(btnGetRFLink);
            btnGetRFLink.setBounds(35, 65, 100, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel23.getComponentCount(); i++) {
                    Rectangle bounds = panel23.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel23.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel23.setMinimumSize(preferredSize);
                panel23.setPreferredSize(preferredSize);
            }
        }
        add(panel23);
        panel23.setBounds(15, 485, 325, 110);

        //======== panel24 ========
        {
            panel24.setPreferredSize(new Dimension(42, 42));
            panel24.setBorder(new TitledBorder("GPIO"));
            panel24.setLayout(null);

            //---- btnSetGPIO ----
            btnSetGPIO.setText("SET");
            btnSetGPIO.addActionListener(e -> btnSetGPIOActionPerformed(e));
            panel24.add(btnSetGPIO);
            btnSetGPIO.setBounds(310, 20, 60, 30);
            panel24.add(cmbRelay);
            cmbRelay.setBounds(70, 20, 235, 30);

            //---- label35 ----
            label35.setText("Relay");
            panel24.add(label35);
            label35.setBounds(25, 25, 55, 17);

            //---- label36 ----
            label36.setText("Input1:");
            panel24.add(label36);
            label36.setBounds(25, 65, 70, 17);

            //---- label37 ----
            label37.setText("Input2:");
            panel24.add(label37);
            label37.setBounds(165, 65, 50, 17);

            //---- btnGetGPIO ----
            btnGetGPIO.setText("GET");
            btnGetGPIO.addActionListener(e -> btnGetGPIOActionPerformed(e));
            panel24.add(btnGetGPIO);
            btnGetGPIO.setBounds(310, 60, 60, 30);
            panel24.add(cmbInput1);
            cmbInput1.setBounds(75, 60, 85, cmbInput1.getPreferredSize().height);
            panel24.add(cmbInput2);
            cmbInput2.setBounds(215, 60, 88, cmbInput2.getPreferredSize().height);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel24.getComponentCount(); i++) {
                    Rectangle bounds = panel24.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel24.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel24.setMinimumSize(preferredSize);
                panel24.setPreferredSize(preferredSize);
            }
        }
        add(panel24);
        panel24.setBounds(710, 355, 380, 100);

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

    private void initUI() {
        for (int k = 1; k <= 30; k++) {
            cmbAnt1Power.addItem(k);
            cmbAnt2Power.addItem(k);
            cmbAnt3Power.addItem(k);
            cmbAnt4Power.addItem(k);
        }
        cbFrequencyBand.addItem("China1(840~845MHz)");
        cbFrequencyBand.addItem("China2(920~925MHz)");
        cbFrequencyBand.addItem("Europe(865~868MHz)");
        cbFrequencyBand.addItem("USA(902~928MHz)");
        cbFrequencyBand.addItem("Korea(917~923MHz)");
        cbFrequencyBand.addItem("Japan(952~953MHz)");
        cbFrequencyBand.addItem("Taiwan(920~928Mhz)");
        cbFrequencyBand.addItem("South Africa(915~919MHz)");
        cbFrequencyBand.addItem("Peru(915-928 MHz)");
        cbFrequencyBand.addItem("Russia(860MHz-867.6MHz)");

        cmbProtocol.addItem("ISO18000-6C");
        cmbProtocol.addItem("GB/T 29768");
        cmbProtocol.addItem("GJB 7377.1");
        cmbProtocol.addItem("ISO18000-6B");

        cmbRFLink.addItem("DSB_ASK/FM0/40KH");
        cmbRFLink.addItem("PR_ASK/Miller4/250KHz");
        cmbRFLink.addItem("PR_ASK/Miller4/300KHz");
        cmbRFLink.addItem("DSB_ASK/FM0/400KHz");

        cmbEPCAndTIDUserMode.addItem("EPC");
        cmbEPCAndTIDUserMode.addItem("EPC+TID");
        cmbEPCAndTIDUserMode.addItem("EPC+TID+USER");

        cmbWorkMode.addItem("Command operating mode");
        cmbWorkMode.addItem("Automatic operation mode");

        cmb_Gen2_session.addItem("S0");
        cmb_Gen2_session.addItem("S1");
        cmb_Gen2_session.addItem("S2");
        cmb_Gen2_session.addItem("S3");
        cmb_Gen2_target.addItem("A");
        cmb_Gen2_target.addItem("B");

        rbtn_Beep_On.setSelected(true);
        rbtn_Beep_Off.setSelected(false);

        cmbRelay.addItem("break off");
        cmbRelay.addItem("close");
        cmbInput1.addItem("Low electrical frequency");
        cmbInput1.addItem("High electrical frequency");
        cmbInput2.addItem("Low electrical frequency");
        cmbInput2.addItem("High electrical frequency");




    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JComboBox cmbAnt1Power;
    private JComboBox cmbAnt2Power;
    private JLabel label2;
    private JComboBox cmbAnt3Power;
    private JLabel label3;
    private JComboBox cmbAnt4Power;
    private JLabel label4;
    private JButton btnGetPower;
    private JButton btnSetPoer;
    private JPanel panel2;
    private JCheckBox cbANT1;
    private JCheckBox cbANT2;
    private JCheckBox cbANT3;
    private JCheckBox cbANT4;
    private JButton btnSetANT;
    private JButton btnGetANT;
    private JPanel panel3;
    private JLabel label5;
    private JComboBox cbFrequencyBand;
    private JButton btnGetFrequencyBand;
    private JButton btnSetFrequencyBand;
    private JPanel panel5;
    private JLabel label6;
    private JComboBox cmbProtocol;
    private JButton btnSetProtocol;
    private JButton btnGetProtocol;
    private JPanel panel17;
    private JLabel label22;
    private JComboBox cmbEPCAndTIDUserMode;
    private JButton btnGetEPCAndTIDUserMode;
    private JButton btnSetEPCAndTIDUserMode;
    private JLabel label23;
    private JTextField tf_user_prt;
    private JLabel label24;
    private JTextField tf_user_len;
    private JPanel panel18;
    private JLabel label25;
    private JComboBox cmb_Gen2_session;
    private JLabel label26;
    private JComboBox cmb_Gen2_target;
    private JButton btnSetGen2;
    private JButton btnGetGen2;
    private JPanel panel19;
    private JLabel label27;
    private JRadioButton rbtn_Beep_On;
    private JRadioButton rbtn_Beep_Off;
    private JButton btnGetBeep;
    private JButton btnSetBeep;
    private JPanel panel20;
    private JLabel label28;
    private JTextField textFieldIP;
    private JLabel label29;
    private JLabel label30;
    private JTextField textFieldSubNetMask;
    private JTextField textFieldGateWay;
    private JButton btnGetLocal_IP;
    private JButton btnSetLocal_IP;
    private JLabel label33;
    private JTextField textFieldPort;
    private JPanel panel21;
    private JLabel label31;
    private JTextField textFieldDestinationIP;
    private JLabel label32;
    private JTextField textFieldDestinationPort;
    private JButton btnSetDestinationPort;
    private JButton btnGetDestinationIP;
    private JPanel panel22;
    private JLabel label34;
    private JComboBox cmbWorkMode;
    private JButton btnSetWorkMode;
    private JButton btnGetWorkMode;
    private JPanel panel23;
    private JComboBox cmbRFLink;
    private JLabel label7;
    private JButton btnSetRFLink;
    private JButton btnGetRFLink;
    private JPanel panel24;
    private JButton btnSetGPIO;
    private JComboBox cmbRelay;
    private JLabel label35;
    private JLabel label36;
    private JLabel label37;
    private JButton btnGetGPIO;
    private JComboBox cmbInput1;
    private JComboBox cmbInput2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
