package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.layout.Layout;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

/**
 * Manage overall control of the robot during teleop, specifically wrt the intake and launch modes,
 * and directing *how* the robot should be controlled. It is meant to be highly encapsulated, with
 * as many methods as possible in other classes.
 */
public class Control {
    private Layout layout;
    private Hardware hardware;
    private Drive drive;
    private Tel tel;
    private OpMode opMode;
    private Limelight limelight;
    private Pathing pathing;

    private TelemetryManager telemetryM;

    public enum Colour {
        RED,
        BLUE,
    }

    public enum Distance {
        CLOSE,
        FAR,
    }

    public enum Mode {
        TELE,
        AUTO,
    }

    // Enabled once the robot mode has been started --- i.e. don't do anything
    // of import until then
    private boolean started = false;

    // Used in determining whether to run the tele or auto procedure
    private Mode mode;

    // Once the control hub stops the robot
    private boolean stopped = false;

    private Colour colour;

    /**
     * Controls the robot's functions
     *
     * @param opMode the OpMode object
     */
    public Control(OpMode opMode, Mode mode, Distance distance, Colour colour) {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        this.opMode = opMode;

        this.mode = mode;
        this.distance = distance;
        this.colour = colour;

        layout = new Layout(opMode);
        hardware = new Hardware(opMode);
        tel = new Tel(opMode, hardware);
        limelight = new Limelight(opMode, hardware, telemetryM);
        drive = new Drive(opMode, hardware, layout, limelight);
        pathing = new Pathing(opMode, hardware);

        update();
    }

    /**
     * Starts the robot
     */
    public void start() {
        started = true;
    }

    /**
     * Enables stopped state, actual changes happen in Update schedule
     */
    public void stop() {
        stopped = true;
    }

    // Schedule that continuously runs while the robot is stopped
    private void stopped() {
        drive.stopRobot();
    }

    private void runAuto() {
    }

    /**
     * Updates drive, telemetry, and the limelight.
     */
    public void update() {
        if (started) {
            switch (mode) {
                case TELE:
                    runTele();
                    break;

                case AUTO:
                    runAuto();
                    break;
            }
            if (mode == Mode.AUTO) {
                runAuto();
            } else {
                runTele();
            }
        }

        if (stopped) {
            stopped();
        }

        opMode.telemetry.addLine("running");

        // TODO: move to tel
        // telemetryM.debug("position", follower.getPose());
        // telemetryM.debug("velocity", follower.getVelocity());

        telemetryM.debug("right stick x amount", opMode.gamepad1.right_stick_x);
        telemetryM.debug("right stick y amount", opMode.gamepad1.right_stick_y);

        telemetryM.debug("driveForwardAmount", layout.driveForwardAmount());
        telemetryM.debug("driveStrafeAmount", layout.driveStrafeAmount());
        telemetryM.debug("driveYawAmount", layout.driveYawAmount());

        opMode.telemetry.addData("time: ", opMode.time);

        telemetryM.update();
        tel.update();
        limelight.update(colour);
        drive.update();
    }

    /**
     * Runs the robot functions
     */
    private void runTele() {
        drive.gamepadDrive();
        pathing.update();

        // // specific buttons don't matter
        // if (layout.advance()) {
        //     // run advance servos
        //     launch.run_advance();
        // }
        // if (layout.launch()) {
        //     // run flywheel, just at a given power
        //     launch.run_flywheel();
        // }
        // launch.runGamepad();
    }
}
