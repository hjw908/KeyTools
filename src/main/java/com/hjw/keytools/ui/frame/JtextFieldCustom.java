package com.hjw.keytools.ui.frame;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;

/**
 * 自定义文本域对象
 * 可以实现粘贴、复制、剪切功能
 * @Classname:JtextFieldCustom
 * @Desription:自定义文本域对象可以实现粘贴、复制、剪切功能
 * @author: hjw
 * @date: 2020.2.19 16:55
 */
public class JtextFieldCustom extends JTextField implements MouseListener {
    private JPopupMenu popupMenu;
    private JMenuItem copy;
    private JMenuItem paste;
    private JMenuItem cut;


    public JtextFieldCustom() {
        super();
        init();
    }



    public void init(){
        this.addMouseListener(this);
        popupMenu = new JPopupMenu();
        copy = new JMenuItem("复制");
        paste = new JMenuItem("粘贴");
        cut = new JMenuItem("剪切");
        popupMenu.add(copy);
        popupMenu.add(paste);
        popupMenu.add(cut);

        copy.setAccelerator(KeyStroke.getKeyStroke('c', InputEvent.CTRL_DOWN_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_DOWN_MASK));
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });
        this.add(popupMenu);
    }


    public void action(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals(copy.getText())) { // 复制
            this.copy();
        } else if (str.equals(paste.getText())) { // 粘贴
            this.paste();
        } else if (str.equals(cut.getText())) { // 剪切
            this.cut();
        }
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }

    public boolean isClipboardString(){
        boolean strFlag = false;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable trans = clipboard.getContents(null);
        if(trans!=null){
            if(trans.isDataFlavorSupported(DataFlavor.stringFlavor)){
                strFlag = true;
                return strFlag;
            }
        }

        return strFlag;
    }

    public boolean isCanCopy() {
        boolean b = false;
        int start = this.getSelectionStart();
        int end = this.getSelectionEnd();
        if (start != end)
            b = true;
        return b;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
            copy.setEnabled(isCanCopy());
            paste.setEnabled(isClipboardString());
            cut.setEnabled(isCanCopy());
            popupMenu.show(this, mouseEvent.getX(), mouseEvent.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
