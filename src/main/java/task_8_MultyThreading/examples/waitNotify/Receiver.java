package task_8_MultyThreading.examples.waitNotify;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class create thread that receive common data
 *
 * email : s.lakhmenev@andersenlab.com
 *
 * @author Lakhmenev Sergey
 * @version 1.1
 */
public class Receiver implements Runnable {
    private Data load;

    Receiver(Data load) {
        this.load = load;
    }

    public void run() {

        while (true) {
            String receivedMessage = load.receive();

            if (receivedMessage.equalsIgnoreCase("end")) {
                break;
            }

            System.out.println(receivedMessage);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}
