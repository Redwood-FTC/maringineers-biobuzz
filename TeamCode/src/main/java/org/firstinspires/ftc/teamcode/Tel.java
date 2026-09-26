package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// TODO: remove this file. in each class, have telemetry in a 'tel' function. Call that function
//  from Control.

/**
 * Manages printing telemetry.
 */
public class Tel {
    private Hardware hardware;
    // private Hang hang;
    private OpMode opMode;
    private Telemetry telemetry;

    private boolean printServos = false;
    private boolean printGamepad = false;
    private boolean printMotors = true;
    private boolean printLimitSwitches = false;
    private boolean printPedroPathing = true;
    private boolean printLimelight = false;

    /**
     * Initialises the OpMode, Hardware, and Telemetry objects.
     *
     * @param opMode   the OpMode object
     * @param hardware the Hardware object
     */
    public Tel(OpMode opMode, Hardware hardware) {
        this.opMode = opMode;
        this.hardware = hardware;
        this.telemetry = opMode.telemetry;
    }

    public Telemetry telemetry() {
        return telemetry;
    }

    /**
     * Updates telemetry.
     */
    public void update() {
        // TODO: order
        if (printServos) {
            telemetry.addLine("SERVOS:");
        }
        if (printMotors) {
            telemetry.addLine("\nMOTORS:");
        }
        if (printLimitSwitches) {
            telemetry.addLine("\nLIMIT SWITCHES:");
        }
        if (printGamepad) {
            telemetry.addLine("\nGAMEPAD:");
            telemetry.addData("right stick x amount: ", opMode.gamepad1.right_stick_x);
            telemetry.addData("right stick y amount: ", opMode.gamepad1.right_stick_y);
        }
        if (printPedroPathing) {
            telemetry.addLine("\nPEDROPATHING:");
        }
        if (printLimelight) {
            telemetry.addLine("\nLIMELIGHT:");
        }

        telemetry.update();
    }
}
