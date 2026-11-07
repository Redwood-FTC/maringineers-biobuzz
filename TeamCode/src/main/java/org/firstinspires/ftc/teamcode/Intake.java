package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Manages the robot's intake
 */
public class Intake {
    private Hardware hardware;
    private OpMode opMode;
    private Layout layout;

    /**
     * Initialises the intake class
     * @param opMode the opMode object
     * @param hardware the hardware object
     * @param layout the layout object
     */
    public Intake(OpMode opMode, Hardware hardware, Layout layout) {
        this.opMode = opMode;
        this.hardware = hardware;
        this.layout = layout;
    }

    /**
     * Uses the gamepad inputs to manage the intake motor.
     */
    public void runGamepad() {
      if (layout.intake()) {
        hardware.intakeMotor.setPower(1);
      } else {
        hardware.intakeMotor.setPower(0);
      }
    }
}
