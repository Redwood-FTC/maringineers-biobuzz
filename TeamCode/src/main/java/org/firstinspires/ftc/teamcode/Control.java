package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


/**
 * Manage overall control of the robot during teleop, specifically wrt the intake and launch modes,
 * and directing *how* the robot should be controlled. It is meant to be highly encapsulated, with
 * as many methods as possible in other classes.
 */
public class Control {
    private Layout layout;
    private Hardware hardware;
    private Drive drive;
    private OpMode opMode;
    private Limelight limelight;
    private Launch launch;
    private Intake intake;

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
    private Distance distance;

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
        limelight = new Limelight(opMode, hardware, telemetryM);
        drive = new Drive(opMode, hardware, layout, limelight);
        launch = new Launch(hardware.launchMotor, layout, hardware);
        intake = new Intake(opMode, hardware, layout);

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
        stopped();
    }

    /**
     * Stops the robot and launch motor
     */
    private void stopped() {
        drive.stopRobot();
        launch.stop();
    }

    /**
     * Runs the auto
     */
    private void runAuto() {
        drive.runAuto(distance, colour);
    }

    /**
     * Updates drive, telemetry, and the limelight.
     */
    public void update() {
        if (started && !stopped) {
            switch (mode) {
                case TELE:
                    runTele();
                    break;

                case AUTO:
                    runAuto();
                    break;
            }
        }

        if (stopped) {
            stopped();
        }

        limelight.update(colour);
        drive.update();

        // Collect readings before sending them to the Driver Station and Panels.
        opMode.telemetry.addData("Status", stopped ? "Stopped" : started ? "Running" : "Ready");
        opMode.telemetry.addData("Time", opMode.time);
        layout.tel(telemetryM);
        drive.tel(opMode.telemetry);
        launch.tel(opMode.telemetry);
        limelight.tel();
        opMode.telemetry.update();
        telemetryM.update();
    }

    /**
     * Runs the robot functions
     */
    private void runTele() {
        drive.gamepadDrive();
        intake.runGamepad();

        launch.runGamepad();
    }
}
