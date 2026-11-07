package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Manages the controller layout.
 * If we have a fancy control scheme, multiple modes, etc., ENCAPSULATE
 * THAT HERE, don't stick that in Control or whatever.
 */
public class Layout {
    private OpMode opMode;
    private Gamepad gamepad1;
    private Gamepad gamepad2;

    /**
     * Initialises the OpMode and gamepad objects.
     * @param opMode the OpMode object
     */
    public Layout(OpMode opMode) {
        this.opMode = opMode;
        gamepad1 = opMode.gamepad1;
        gamepad2 = opMode.gamepad2;
    }

    /**
     * Returns whether a on gamepad1 is pressed
     * @return whether a on gamepad 1 is presssed
     */
    public boolean intake() {
        return gamepad1.a;
    }

    /**
     * Returns whether b on gamepad 1 is pressed
     * @return whether b on gamepad 1 is pressed
     */
    public boolean tmpAim() {
        return gamepad1.b;
    }

    // TODO: tune this, if necessary so it feels better

    /**
     * Controls driving the robot forward/back.
     *
     * @return how far forward/back
     */
    public double driveForwardAmount() {
        return Math.pow(gamepad1.left_stick_y, 3);
    }

    /**
     * Controls strafing the robot.
     * @return how far lef/right
     */
    public double driveStrafeAmount() {
        return Math.pow(gamepad1.left_stick_x, 3);
    }

    /**
     * Controls turning the robot. Use triggers for rotation so controls are simpler.
     */
    public double driveYawAmount() {
        return gamepad1.left_trigger - gamepad1.right_trigger;
    }

    // TODO: DO THE NEEDFUL REFACTOR OF SPECIFIC LOGIC

    boolean launch = false;
    boolean launchPressed = false;
    /**
     * Runs the launch motor.
     * @return whether X is pressed
     */
    public boolean launch() {
        // TODO: this button is temporary/to be decided as we figure out our
        // actual layout

        if (gamepad1.x && !launchPressed) {
            launch = !launch;
            launchPressed = true;
        } else if (!gamepad1.x && launchPressed) {
            launchPressed = false;
        }

      return launch;
    }

    boolean increaseLaunchSpeedPressed = false;
    /**
     * Increases launch speed
     * @return if launch speed should be increased
     */
    public boolean increaseLaunchSpeed() {
        if (gamepad1.dpad_up && !increaseLaunchSpeedPressed) {
            increaseLaunchSpeedPressed = true;
            return true;
        }
        return false;
    }

    boolean decreaseLaunchSpeedPressed = false;
    /**
     * Decreases launch speed
     * @return whether launch speed should be decreased
     */
    public boolean decreaseLaunchSpeed() {
        if (gamepad1.dpad_down && !decreaseLaunchSpeedPressed) {
            decreaseLaunchSpeedPressed = true;
            return true;
        }
        return false;
    }

//    public void update() {
//
//    }

    /**
     * Sends telemetry to the driver station
     * @param telemetry object from the Telemetry class
     */
    public void tel(TelemetryManager telemetry) {
        telemetry.debug("right stick x amount", gamepad1.right_stick_x);
        telemetry.debug("right stick y amount", gamepad1.right_stick_y);
        telemetry.debug("driveForwardAmount", driveForwardAmount());
        telemetry.debug("driveStrafeAmount", driveStrafeAmount());
        telemetry.debug("driveYawAmount", driveYawAmount());
        telemetry.debug("Launch button", launch());
    }
}
