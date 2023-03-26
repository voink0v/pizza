import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

class Pizzeria{
    class Order{
        String pizzaName;
        long orderTime;
        Order(String pizzaName, long orderTime){
            this.pizzaName = pizzaName;
            this.orderTime = orderTime;
        }
    }
    LinkedBlockingDeque<Order> orders = new LinkedBlockingDeque<>();
    final  long START_TIME;
    Pizzeria(){
        START_TIME = System.currentTimeMillis();
        new PizzaCar().start();
        new PizzaCar().start();
    }
    class PizzaCar extends Thread{

        public void run(){
            while(System.currentTimeMillis() -  START_TIME < 5000){
                Order order = null;
                try{
                    order = orders.poll(10, TimeUnit.MILLISECONDS);
                }catch(InterruptedException e){
                    break;
                }
                if (order != null){
                    if (System.currentTimeMillis() + 500 - order.orderTime <= 750){
                        //принять заказ
                        try{
                            Thread.sleep(500);
                        }catch(InterruptedException e){
                            break;
                        }
                        System.out.println(order.pizzaName + " is delivered");
                    }
                    else{
                        //отменить заказ
                        System.out.println(order.pizzaName + " is NOT delivered");
                    }
                }
            }
        }
    }
    void order(String pizzaName){
        try{
            orders.put(new Order(pizzaName, System.currentTimeMillis()));
        }catch(InterruptedException e){
            return;
        }
    }
}