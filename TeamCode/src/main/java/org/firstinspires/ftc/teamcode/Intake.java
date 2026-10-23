package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.layout.Layout;

import java.util.Arrays;

// file containing public interface for driving, primarily controls pedropathing
// etc.
public class Intake {
    private Hardware hardware;
    private OpMode opMode;
    private Layout layout;

    public Intake(OpMode opMode, Hardware hardware, Layout layout) {
        this.opMode = opMode;
        this.hardware = hardware;
        this.layout = layout;
    }

    // public void update() {
    //     // follower.update();
    // }

    public void runGamepad() {
      if (layout.intake()) {
        hardware.intakeMotor.setPower(1);
      } else {
        hardware.intakeMotor.setPower(0);
      }
    }
}
