package team10.sumato;

import android.util.Log;

public class MotorMovement {

    private int xLimit = 80;
    private int yLimit = 100;
    private int sendingDelay = 15;
    private int offset = 150;

    private double xTotal = 0;
    private double yTotal = 0;

    private int counter = 0;
    private int averageX = 150;
    private int averageY = 150;

    /*
     * Initialize the motor movement with custom values.
     */
    public MotorMovement(int xLimit, int yLimit, int sendingDelay, int offset) {
        this.xLimit = xLimit;
        this.yLimit = yLimit;
        this.sendingDelay = sendingDelay;
        this.offset = offset;
    }
    /*
     * Default values for the motor movement;
     */
    public MotorMovement() {
        this.xLimit = 80;
        this.yLimit = 100;
        this.sendingDelay = 15;
        this.offset = 150;
    }

    /*
     * Receives a rotation matrix that is parsed and sent to the server.
     * Computes the average (according sendingDelay) and rounds the number to the nearest 10th before sending it.
     */
    public void addValues(float[] mat) {
        double x = Math.toDegrees(-Math.asin(mat[8]));
        double y = Math.toDegrees(Math.atan2(mat[9], mat[10]));

        if (counter < sendingDelay) {
            xTotal = this.xTotal + x;
            yTotal = this.yTotal + y;
            counter++;
        } else {
            averageX = (int) Math.round((xTotal / counter) );
            averageY = (int) Math.round((yTotal / counter) );
            sendMovement(averageX, averageY);
            counter = 0;
            xTotal = 0;
            yTotal = 0;
        }
    }
    /*
     * Send a formatted string "pos(x,y)" to the server.
     * Checks if the values are within the bounds before sending it and adds the offset for the camera movement.
     */
    private void sendMovement(int x, int y) {
        if (-xLimit < x && x < xLimit && -yLimit < y && y < yLimit) {
            x = x + offset;
            y = -y + offset;
            String str = "pos(" + y + "," + x + ")";
            TCPManager.getInstance().getClient().send(str);
            Log.d("motor", str);
        } else {
            Log.d("motors", "out of bounds");
        }
    }

}
