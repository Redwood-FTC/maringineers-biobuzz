package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Manages the launch motor
 */
public class Launch {
    private final DcMotorEx motor;
    private final Layout layout;
    private double speed = -0.4;
    private double targetSpeed;

    /**
     * Initialises the launch motor
     * @param motor motor object
     * @param layout layout object
     */
    public Launch(DcMotorEx motor, Layout layout) {
        this.motor = motor;
        this.layout = layout;

        // updateSpeed();
    }

    /**
     * Updates the robot speed
     */
    private void updateSpeed() {
        targetSpeed = speed * motor.getMotorType().getMaxRPM()
                * motor.getMotorType().getTicksPerRev() / 60.0;
    }

    /**
     * Lets you control the launch motor speed
     */
    public void runGamepad() {
        if (layout.increaseLaunchSpeed()) {
            speed += .02;
        } else if (layout.decreaseLaunchSpeed()) {
            speed -= .02;
        }

        if (layout.launch()) {
            updateSpeed();
        } else {
            targetSpeed = 0;
        }
        motor.setVelocity(targetSpeed);
    }

    /**
     * Stops the launch motor
     */
    public void stop() {
        targetSpeed = 0;
        motor.setVelocity(0);
        motor.setPower(0);
    }

    /**
     * Sends telemetry to the driver station
     * @param telemetry the telemetry object from the Telemetry class
     */
    public void tel(Telemetry telemetry) {
        telemetry.addData("Launch speed", speed);
        telemetry.addData("Launch target (ticks/s)", targetSpeed);
        telemetry.addData("Launch actual (ticks/s)", motor.getVelocity());
        telemetry.addData("Launch position (ticks)", motor.getCurrentPosition());
    }
}
