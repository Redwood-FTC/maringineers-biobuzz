package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import static org.firstinspires.ftc.teamcode.Control.Colour;
import static org.firstinspires.ftc.teamcode.Control.Mode;
/**
 * Manages the robot's autonomous.
 */
@Autonomous(name = "Red Autonomous", group = "Auto")
public class RedAutonomous extends OpMode {
    private Control control;

    /**
     * Initialises the control object.
     */
    public void init() {
        control = new Control(this, Mode.AUTO, Colour.RED);
    }

    /**
     * For code that runs CONTINUOUSLY during Init.
     */
    public void init_loop() {
        control.update();
    }

    /**
     * Run autonomous.
     */
    public void start() {
        control.start();
    }

    /**
     * Update control
     */
    public void loop() {
        control.update();
    }

    /**
     * Does nothing yet.
     */
    public void stop() {

    }
}
