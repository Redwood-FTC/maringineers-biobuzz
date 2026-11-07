package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Manages the launch motor
 */
public class Launch {
    private final DcMotorEx motor;
    private final Layout layout;
    private Hardware hardware;
    private double speed = 0.4;
    private double targetSpeed;
    private double tmpSpeed = speed;
    // private boolean toggledOn = false;
    // private boolean lastLaunchPressed = false;

    /**
     * Initialises the launch motor
     * @param motor motor object
     * @param layout layout object
     */
    public Launch(DcMotorEx motor, Layout layout, Hardware hardware) {
        this.motor = motor;
        this.layout = layout;
        this.hardware = hardware;

        // updateSpeed();
    }

    /**
     * Updates the launch motor speed
     */
    private void updateSpeed() {
        targetSpeed = speed * motor.getMotorType().getMaxRPM()
                * motor.getMotorType().getTicksPerRev() / 60.0;
        tmpSpeed = speed;
    }

    /**
     * Lets you control the launch motor speed
     */
    public void runGamepad() {
        if (layout.increaseLaunchSpeed()) {
            speed += .02;
            // if (toggledOn) {
                updateSpeed();
            // }
        } else if (layout.decreaseLaunchSpeed()) {
            speed -= .02;
            // if (toggledOn) {
                updateSpeed();
            // }
        }

        // boolean currentLaunchPressed = layout.launch();

        // if (currentLaunchPressed && !lastLaunchPressed) {
        //     toggledOn = !toggledOn;
        // }
        // lastLaunchPressed = currentLaunchPressed;

        if (layout.launch()) {
            updateSpeed();
        } else {
            targetSpeed = 0;
            tmpSpeed = 0;
        }

        // TODO, figure out details
        // motor.setVelocity(targetSpeed);
        motor.setPower(tmpSpeed);
    }

    /**
     * Stops the launch motor
     */
    public void stop() {
        // toggledOn = false;
        // lastLaunchPressed = false;
        targetSpeed = 0;
        tmpSpeed = 0;
        motor.setVelocity(0);
        motor.setPower(0);
        hardware.launchMotorSetStop();
    }

    /**
     * Sends telemetry to the driver station
     * @param telemetry the telemetry object from the Telemetry class
     */
    public void tel(Telemetry telemetry) {
        // telemetry.addData("Launch toggled on", toggledOn);
        telemetry.addData("Launch speed", speed);
        telemetry.addData("Launch target (ticks/s)", targetSpeed);
        telemetry.addData("Launch power (ticks/s)", tmpSpeed);
        telemetry.addData("Launch actual (ticks/s)", motor.getVelocity());
        telemetry.addData("Launch position (ticks)", motor.getCurrentPosition());
    }
}
