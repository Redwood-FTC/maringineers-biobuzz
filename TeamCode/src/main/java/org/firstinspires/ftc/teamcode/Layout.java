package org.firstinspires.ftc.teamcode.layout;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Manages the controller layout.
 */
public class Layout {
    private OpMode opMode;
    private Gamepad gamepad1;
    private Gamepad gamepad2;

    // TODO: if we have a fancy control scheme, multiple modes etc. ENCAPSULATE THAT HERE, don't
    //  stick that in Control or whatever

    /**
     * Initialises the OpMode and gamepad objects.
     *
     * @param opMode the OpMode object
     */
    public Layout(OpMode opMode) {
        this.opMode = opMode;
        gamepad1 = opMode.gamepad1;
        gamepad2 = opMode.gamepad2;
    }

    /**
     * Controls driving the robot forward/back.
     *
     * @return how far forward/back
     */
    public double driveForwardAmount() {
        return -Math.pow(gamepad1.left_stick_y, 3);
    }

    /**
     * Controls strafing the robot.
     *
     * @return how far lef/right
     */
    public double driveStrafeAmount() {
        return -Math.pow(gamepad1.left_stick_x, 3);
    }

    /**
     * Controls turning the robot. Use triggers for rotation so controls are simpler.
     */
    public double driveYawAmount() {
        return gamepad1.left_trigger - gamepad1.right_trigger;
    }
}
