package Storage.UI;

import Storage.Model.Order;
import Storage.Model.StorageManager;

import javax.swing.*;
import java.awt.*;

public class Animation {
    private Timer _timer;
    private int _movingTime = 150;
    private Component  component;

    private double _velX, _velY;
    private int _x,_y;
    private int _destinationX, _destinationY;

    public Animation(JPanel jPanel , int x, int y, int destinationX, int destinationY, String text , StorageManager manager, boolean EndAnimationFlag, Order order){
        InitTimer(jPanel, manager, EndAnimationFlag, order);
        SetCoordinates(x, y, destinationX, destinationY);
        AddComponent(jPanel, text);
        _timer.start();
    }
    private  void InitTimer(JPanel jPanel, StorageManager manager, boolean EndAnimationFlag, Order order){
        _timer = new Timer(5, e -> {
            if(!TryMove()) {
                jPanel.remove(component);
                if(EndAnimationFlag){
                    manager.processOrder(order);
                }else{
                }
            }
            jPanel.repaint();
        });
    }
    private void SetCoordinates(int x, int y, int destinationX, int destinationY){
        this._destinationX = destinationX;
        this._destinationY = destinationY;
        this._x = x;
        this._y = y;

        this._velX = (destinationX - x)/_movingTime;
        this._velY = (destinationY - y)/_movingTime;
    }
    private void AddComponent(JPanel jPanel , String text){
        component = new JLabel();
        component.setBounds(_x, _y, 10, 10);
        ((JLabel) component).setText(text);
        jPanel.add(component);
    }

    private boolean TryMove(){
        if(_x == _destinationX || _y ==  _destinationY) {
            _timer.stop();
            return false;
        }else{
            this._x += this._velX;
            this._y += this._velY;
            component.setBounds(_x, _y, 200, 50);
            return true;
        }
    }
}
