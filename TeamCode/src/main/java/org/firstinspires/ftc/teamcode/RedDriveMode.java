package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.Control.Colour;
import static org.firstinspires.ftc.teamcode.Control.Distance;
import static org.firstinspires.ftc.teamcode.Control.Mode;

/**
 * The primary drive mode class. Most of the code is encapsulated in other classes, this should just
 * initialise it.
 */
@TeleOp(name = "Red Drive Mode", group = "Drive")
public class RedDriveMode extends OpMode {
    private Control control;

    // if runInit is the run for init mode, and initrun is the init for
    // run mode, then initinit is the init for init mode
    public void init() {
        control = new Control(this, Mode.TELE, Distance.FAR, Colour.RED);
    } // FAR dDistance is a placeholder

    /**
     * For code that runs CONTINUOUSLY during init.
     */
    public void init_loop() {
        control.update();
    }

    /**
     * Starts control.
     */
    public void start() {
        control.start();
    }

    /**
     * Updates control.
     */
    public void loop() {
        control.update();
    }

    /**
     * Stops control.
     */
    public void stop() {
        control.stop();
    }
}
